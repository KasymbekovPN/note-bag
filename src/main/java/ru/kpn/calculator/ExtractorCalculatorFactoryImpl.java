package ru.kpn.calculator;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.IterableExtractor;
import ru.kpn.subscriber.Subscriber;

public class ExtractorCalculatorFactoryImpl implements ExtractorCalculatorFactory<Update, BotApiMethod<?>> {
    @Override
    public ExtractorCalculator<Update, BotApiMethod<?>> create(Subscriber<Update, BotApiMethod<?>> subscriber) {
        return new SimpleExtractorCalculator(new IterableExtractor(subscriber));
    }
}
