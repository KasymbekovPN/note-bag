package ru.kpn.bpp.subscriptionManager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.statusSeed.RawMessageOld;
import ru.kpn.strategy.injectors.ExtractorInjector;
import ru.kpn.strategy.injectors.MatcherInjector;
import ru.kpn.strategy.injectors.PriorityInjector;
import ru.kpn.strategy.strategies.Strategy;
import ru.kpn.subscriber.Subscriber;
import ru.kpn.subscriber.SubscriberFactory;
import ru.kpn.subscriptionManager.SubscriptionManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

@Slf4j
@Component
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class SubscriptionManagerBPP implements BeanPostProcessor {

    @Autowired
    private PriorityInjector priorityInjector;
    @Autowired
    private MatcherInjector matcherInjector;
    @Autowired
    private ExtractorInjector extractorInjector;

    @Autowired
    private SubscriberFactory<Update, BotApiMethod<?>> subscriberFactory;
    @Autowired
    private SubscriptionManager<Update, BotApiMethod<?>> subscriptionManager;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional<Strategy<Update, BotApiMethod<?>>> maybeStrategy = checkBean(bean);
        if (maybeStrategy.isPresent()) {
            StatusCollector collect = new StatusCollector()
                    .collect(priorityInjector.inject(bean))
                    .collect(extractorInjector.inject(bean))
                    .collect(matcherInjector.inject(bean));
            if (collect.isSuccess()){
                subscriptionManager.subscribe(createSubscriber((Strategy<Update, BotApiMethod<?>>) bean));
            } else {
                throw new BeanCreationException(collect.getStatus().getCode());
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

    @Getter
    private static class StatusCollector{
        private RawMessageOld<String> status;
        private boolean success = true;

        public StatusCollector collect(Result<?, RawMessageOld<String>> result){
            if (status == null && !result.getSuccess()){
                success = false;
                status = result.getStatus();
            }
            return this;
        }
    }
}
