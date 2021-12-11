package ru.kpn.bpp.subscriptionManager;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.strategy.calculaters.nameCalculator.NameCalculator;
import ru.kpn.strategy.strategies.Strategy;
import ru.kpn.subscriber.Subscriber;
import ru.kpn.subscriber.SubscriberFactory;
import ru.kpn.subscriptionManager.SubscriptionManager;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class SubscriptionManagerBPP implements BeanPostProcessor {

    @Autowired
    private NameCalculator nameCalculator;

    @Autowired
    private SubscriberFactory<Update, BotApiMethod<?>> subscriberFactory;
    @Autowired
    private SubscriptionManager<Update, BotApiMethod<?>> subscriptionManager;

    @Autowired
    private ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> strategyInitFactory;
    @Autowired
    private ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> extractorFactory;
    @Autowired
    private ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessage<String>> matcherFactory;

    private Map<String, StrategyInitDatum> strategyInitData;
    private Map<String, ExtractorDatum> extractorInitData;
    private Map<String, MatcherDatum> matcherInitData;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional<Strategy<Update, BotApiMethod<?>>> maybeStrategy = checkBean(bean);
        if (maybeStrategy.isPresent()){
            Strategy<Update, BotApiMethod<?>> strategy = maybeStrategy.get();

            Result<String, RawMessage<String>> nameCalcResult = nameCalculator.calculate(bean);
            if (nameCalcResult.getSuccess()){
                String strategyName = nameCalcResult.getValue();
                injectPriority(strategy, strategyName);
                injectExtractor(strategy, strategyName);
                injectMatcher(strategy, strategyName);

                subscriptionManager.subscribe(createSubscriber(strategy));
            }
            else {
                // TODO: 11.12.2021 change exception
                throw new BeanCreationException(nameCalcResult.getStatus().getCode());
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private void injectPriority(Strategy<Update, BotApiMethod<?>> strategy, String strategyName) {
//        String strategyName = calculateStrategyName(strategy);
        Optional<Method> maybeMethod = getMethodForInjection(strategy, InjectionType.PRIORITY);
        if (maybeMethod.isPresent()){
            if (strategyInitData.containsKey(strategyName)){
                StrategyInitDatum datum = strategyInitData.get(strategyName);
                Result<Integer, RawMessage<String>> result = strategyInitFactory.create(datum);
                if (result.getSuccess()){
                    inject(strategy, maybeMethod.get(), result.getValue());
                } else {
                    throw new BeanCreationException(String.format("Failure attempt of priority creation for %s", strategyName));
                }
            } else {
                throw new BeanCreationException(String.format("%s datum doesn't exist", strategyName));
            }
        }
    }

    private void injectExtractor(Strategy<Update, BotApiMethod<?>> strategy, String strategyName) {
//        String strategyName = calculateStrategyName(strategy);
        Optional<Method> maybeMethod = getMethodForInjection(strategy, InjectionType.EXTRACTOR);
        if (maybeMethod.isPresent()){
            if (extractorInitData.containsKey(strategyName)){
                ExtractorDatum datum = extractorInitData.get(strategyName);
                Result<Function<Update, String>, RawMessage<String>> result = extractorFactory.create(datum);
                if (result.getSuccess()){
                    inject(strategy, maybeMethod.get(), result.getValue());
                } else {
                    throw new BeanCreationException(String.format("Failure attempt of extractor creation for %s", strategyName));
                }
            } else {
                throw new BeanCreationException(String.format("%s datum doesn't exist", strategyName));
            }
        }
    }

    private void injectMatcher(Strategy<Update, BotApiMethod<?>> strategy, String strategyName) {
//        String strategyName = calculateStrategyName(strategy);
        Optional<Method> maybeMethod = getMethodForInjection(strategy, InjectionType.MATCHER);
        if (maybeMethod.isPresent()){
            if (matcherInitData.containsKey(strategyName)){
                MatcherDatum datum = matcherInitData.get(strategyName);
                Result<Function<Update, Boolean>, RawMessage<String>> result = matcherFactory.create(datum);
                if (result.getSuccess()){
                    inject(strategy, maybeMethod.get(), result.getValue());
                } else {
                    throw new BeanCreationException(String.format("Failure attempt of extractor creation for %s", strategyName));
                }
            } else {
                throw new BeanCreationException(String.format("%s datum doesn't exist", strategyName));
            }
        }
    }

    // TODO: 10.11.2021 into bean ??? 
    private Optional<Method> getMethodForInjection(Strategy<Update, BotApiMethod<?>> strategy, InjectionType type){
        for (Method declaredMethod : strategy.getClass().getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(Inject.class)){
                Inject annotation = declaredMethod.getAnnotation(Inject.class);
                if (type.equals(annotation.value())){
                    return Optional.of(declaredMethod);
                }
            }
        }
        return Optional.empty();
    }

    @SneakyThrows // TODO: 08.11.2021 ???
    private void inject(Strategy<Update, BotApiMethod<?>> strategy, Method method, Object value) {
        method.invoke(strategy, value);
    }

    // TODO: 03.11.2021 it must be bean 
    private Optional<Strategy<Update, BotApiMethod<?>>> checkBean(Object bean) {
        boolean success = false;
        Type[] genericInterfaces = bean.getClass().getSuperclass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (!(genericInterface instanceof ParameterizedType)){
                continue;
            }
            ParameterizedType pt = (ParameterizedType) genericInterface;
            Type rawType = pt.getRawType();
            success = rawType.getTypeName().equals(Strategy.class.getTypeName());
        }

        return success
                ? Optional.of((Strategy<Update, BotApiMethod<?>>) bean)
                : Optional.empty();
    }

    private Subscriber<Update, BotApiMethod<?>> createSubscriber(Strategy<Update, BotApiMethod<?>> strategy) {
        return subscriberFactory
                .reset()
                .strategy(strategy)
                .build();
    }
}
