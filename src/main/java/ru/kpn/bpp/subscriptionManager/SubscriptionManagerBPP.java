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
import ru.kpn.creator.StrategyExtractorCreator;
import ru.kpn.creator.StrategyInitCreatorOld;
import ru.kpn.creator.StrategyMatcherCreator;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.datum.StrategyInitDatum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.strategy.Strategy;
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

    // TODO: 03.11.2021 how it set?
    private static final String STRATEGY_BEAN_SUFFIX = "Strategy";

    @Autowired
    private StrategyMatcherCreator matcherCreator;
    @Autowired
    private StrategyExtractorCreator extractorCreator;
    @Autowired
    private StrategyInitCreatorOld strategyInitCreatorOld;
    @Autowired
    private StrategyExtractorCreator strategyExtractorCreator;
    @Autowired
    private StrategyMatcherCreator strategyMatcherCreator;
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
            injectPriority(strategy);
            injectExtractor(strategy);
            injectMatcher(strategy);
            createSubscriber(strategy);

            // TODO: 04.12.2021 del
//            String strategyName = calculateStrategyName(strategy);
//            Optional<Method> maybePriorityInjectionMethod = getMethodForInjection(strategy, InjectionType.PRIORITY);
//            if (maybePriorityInjectionMethod.isPresent()){
//                StrategyInitCreatorOld.Result result = strategyInitCreatorOld.getDatum(strategyName);
//                if (result.getSuccess()){
//                    inject(strategy, maybePriorityInjectionMethod.get(), result.getPriority());
//                } else {
//                    // TODO: 08.11.2021 use rawMessage
//                    throw new BeanCreationException(String.format("Priority for '%s' doesn't exist", strategyName));
//                }
//            }
//
//            Optional<Method> maybeExtractorInjectionMethod = getMethodForInjection(strategy, InjectionType.EXTRACTOR);
//            if (maybeExtractorInjectionMethod.isPresent()){
//                StrategyExtractorCreator.Result result = strategyExtractorCreator.getOrCreate(strategyName);
//                if (result.getSuccess()){
//                    inject(strategy, maybeExtractorInjectionMethod.get(), result.getExtractor());
//                } else {
//                    // TODO: 10.11.2021 use exception with rawMessage
//                    throw new BeanCreationException(String.format("Extractor for '%s' doesn't exist", strategyName));
//                }
//            }
//
//            Optional<Method> maybeMatcherInjectionMethod = getMethodForInjection(strategy, InjectionType.MATCHER);
//            if (maybeMatcherInjectionMethod.isPresent()){
//                StrategyMatcherCreator.Result result = strategyMatcherCreator.getOrCreate(strategyName);
//                if (result.getSuccess()){
//                    inject(strategy, maybeMatcherInjectionMethod.get(), result.getMatcher());
//                } else {
//                    // TODO: 10.11.2021 use exception with rawMessage
//                    throw new BeanCreationException(String.format("Matcher for '%s' doesn't exist", strategyName));
//                }
//            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private void injectPriority(Strategy<Update, BotApiMethod<?>> strategy) {
        String strategyName = calculateStrategyName(strategy);
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

    private void injectExtractor(Strategy<Update, BotApiMethod<?>> strategy) {
        String strategyName = calculateStrategyName(strategy);
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

    private void injectMatcher(Strategy<Update, BotApiMethod<?>> strategy) {
        String strategyName = calculateStrategyName(strategy);
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
    private String calculateStrategyName(Strategy<Update, BotApiMethod<?>> strategy) {
        String simpleName = strategy.getClass().getSimpleName();
        int simpleNameLen = simpleName.length();
        int suffixLen = STRATEGY_BEAN_SUFFIX.length();
        if (simpleNameLen > suffixLen){
            int borderIndex = simpleNameLen - suffixLen;
            String suffix = simpleName.substring(borderIndex, simpleNameLen);
            if (STRATEGY_BEAN_SUFFIX.equals(suffix)){
                char[] chars = simpleName.substring(0, borderIndex).toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                return String.valueOf(chars);
            }
        }
        throw new BeanCreationException(String.format("Strategy bean '%s' isn't ended on '%s'", simpleName, STRATEGY_BEAN_SUFFIX));
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
