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
            String strategyName = strategy.getName();
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
