package ru.kpn.tube.calculator;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.extractor.TubeSubscriberExtractorImpl;
import ru.kpn.tube.subscriber.TubeSubscriber;

public class SubscriberCalculatorFactoryImpl implements SubscriberCalculatorFactory<Update, BotApiMethod<?>> {
    @Override
    public SubscriberCalculator<Update, BotApiMethod<?>> create(TubeSubscriber<Update, BotApiMethod<?>> subscriber) {
        return new SubscriberCalculatorImpl(new TubeSubscriberExtractorImpl(subscriber));
    }
}
