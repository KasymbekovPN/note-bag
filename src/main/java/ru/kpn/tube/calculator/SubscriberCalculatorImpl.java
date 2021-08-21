package ru.kpn.tube.calculator;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.extractor.TubeSubscriberExtractor;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

public class SubscriberCalculatorImpl implements SubscriberCalculator<TubeMessage, BotApiMethod<?>> {

    private final TubeSubscriberExtractor<TubeMessage, BotApiMethod<?>> extractor;

    public SubscriberCalculatorImpl(TubeSubscriberExtractor<TubeMessage, BotApiMethod<?>> extractor) {
        this.extractor = extractor;
    }

    @Override
    public Optional<BotApiMethod<?>> calculate(TubeMessage message) {
        Optional<TubeSubscriber<TubeMessage, BotApiMethod<?>>> maybe;
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
