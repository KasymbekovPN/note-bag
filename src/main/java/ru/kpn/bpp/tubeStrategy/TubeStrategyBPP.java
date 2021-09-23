package ru.kpn.bpp.tubeStrategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bot.publisher.Publisher;
import ru.kpn.strategy.Strategy;
import ru.kpn.subscriber.Subscriber;
import ru.kpn.subscriber.SubscriberFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
@Component
public class TubeStrategyBPP implements BeanPostProcessor {

    @Autowired
    private SubscriberFactory<Update, BotApiMethod<?>, Integer> subscriberFactory;

    @Autowired
    private Publisher<Update, BotApiMethod<?>> npBot;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isBeanTubeStrategy(bean)){
            npBot.subscribe(createSubscriber(bean));
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private Subscriber<Update, BotApiMethod<?>> createSubscriber(Object bean) {
        Strategy<Update, BotApiMethod<?>> strategy = (Strategy<Update, BotApiMethod<?>>) bean;
        return subscriberFactory
                .reset()
                .strategy(strategy)
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
            success = rawType.getTypeName().equals(Strategy.class.getTypeName());
        }

        return success;
    }

}
