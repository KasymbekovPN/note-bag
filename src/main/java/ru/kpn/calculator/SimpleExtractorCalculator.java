package ru.kpn.calculator;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.SubscriberExtractor;
import ru.kpn.subscriber.Subscriber;

import java.util.Optional;

public class SimpleExtractorCalculator implements ExtractorCalculator<Update, BotApiMethod<?>> {

    private final SubscriberExtractor<Update, BotApiMethod<?>> extractor;

    public SimpleExtractorCalculator(SubscriberExtractor<Update, BotApiMethod<?>> extractor) {
        this.extractor = extractor;
    }

    @Override
    public Optional<BotApiMethod<?>> calculate(Update message) {
        Optional<Subscriber<Update, BotApiMethod<?>>> maybe;
        do {
            maybe = extractor.getNext();
            if (maybe.isPresent()){
                Optional<BotApiMethod<?>> executionResult = maybe.get().executeStrategy(message);
                if (executionResult.isPresent()){
                    return executionResult;
                }
            }
        } while (maybe.isPresent());

        return Optional.empty();
    }
}
