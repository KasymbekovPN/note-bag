package ru.kpn.tube.calculator;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.extractor.TubeSubscriberExtractor;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

// TODO: 25.08.2021 rename
public class SubscriberCalculatorImpl implements SubscriberCalculator<Update, BotApiMethod<?>> {

    private final TubeSubscriberExtractor<Update, BotApiMethod<?>> extractor;

    public SubscriberCalculatorImpl(TubeSubscriberExtractor<Update, BotApiMethod<?>> extractor) {
        this.extractor = extractor;
    }

    @Override
    public Optional<BotApiMethod<?>> calculate(Update message) {
        Optional<TubeSubscriber<Update, BotApiMethod<?>>> maybe;
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
