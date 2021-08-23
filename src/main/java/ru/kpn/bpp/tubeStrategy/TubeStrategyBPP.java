package ru.kpn.bpp.tubeStrategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.Tube;
import ru.kpn.tube.strategy.SubscriberStrategy;
import ru.kpn.tube.subscriber.PriorityTubeSubscriber;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
@Component
public class TubeStrategyBPP implements BeanPostProcessor {

    @Autowired
    private Tube<Update, BotApiMethod<?>> tube;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isBeanTubeStrategy(bean)){
            TubeSubscriber<Update, BotApiMethod<?>> subscriber = createSubscriber(bean);
            tube.subscribe(subscriber);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    // TODO: 21.08.2021 ??? is there need creation-service for tube subscriber
    private TubeSubscriber<Update, BotApiMethod<?>> createSubscriber(Object bean) {
        SubscriberStrategy<Update, BotApiMethod<?>> strategy = (SubscriberStrategy<Update, BotApiMethod<?>>) bean;
        return PriorityTubeSubscriber.builder()
                .strategy(strategy)
                .priority(strategy.getPriority())
                .build();
    }

    private boolean isBeanTubeStrategy(Object bean) {
        boolean success = false;
        Type[] genericInterfaces = bean.getClass().getSuperclass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (!(genericInterface instanceof ParameterizedType)){
                continue;
            }
            ParameterizedType pt = (ParameterizedType) genericInterface;
            Type rawType = pt.getRawType();
            success = rawType.getTypeName().equals(SubscriberStrategy.class.getTypeName());
        }

        return success;
    }

}
