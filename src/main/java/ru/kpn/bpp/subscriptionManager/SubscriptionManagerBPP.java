package ru.kpn.bpp.subscriptionManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.creator.StrategyMatcherCreator;
import ru.kpn.strategy.Strategy;
import ru.kpn.subscriber.Subscriber;
import ru.kpn.subscriber.SubscriberFactory;
import ru.kpn.subscriptionManager.SubscriptionManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

@Slf4j
@Component
public class SubscriptionManagerBPP implements BeanPostProcessor {

    // TODO: 03.11.2021 how it set?
    private static final String STRATEGY_BEAN_SUFFIX = "Strategy";

    @Autowired
    private StrategyMatcherCreator matcherCreator;

    @Autowired
    private SubscriberFactory<Update, BotApiMethod<?>> subscriberFactory;

    @Autowired
    private SubscriptionManager<Update, BotApiMethod<?>> subscriptionManager;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional<Strategy<Update, BotApiMethod<?>>> maybeStrategy = checkBean(bean);
        if (maybeStrategy.isPresent()){
            Strategy<Update, BotApiMethod<?>> strategy = maybeStrategy.get();
            String strategyName = calculateStrategyName(strategy);

            StrategyMatcherCreator.Result result = matcherCreator.getOrCreate(strategyName);
            if (result.getSuccess()){
                strategy.setMatcher(result.getMatcher());
                subscriptionManager.subscribe(createSubscriber(strategy));
            } else {
                throw new BeanCreationException(result.getErrorMessage());
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
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
