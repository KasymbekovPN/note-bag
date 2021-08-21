package ru.kpn.tube.extractor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

public class TubeSubscriberExtractorImpl implements TubeSubscriberExtractor<TubeMessage, BotApiMethod<?>> {

    private TubeSubscriber<TubeMessage, BotApiMethod<?>> tubeSubscriber;

    public TubeSubscriberExtractorImpl(TubeSubscriber<TubeMessage, BotApiMethod<?>> tubeSubscriber) {
        this.tubeSubscriber = tubeSubscriber;
    }

    @Override
    public Optional<TubeSubscriber<TubeMessage, BotApiMethod<?>>> getNext() {
        Optional<TubeSubscriber<TubeMessage, BotApiMethod<?>>> maybeNext = tubeSubscriber.getNext();
        maybeNext.ifPresent(tubeMessageBotApiMethodTubeSubscriber -> tubeSubscriber = tubeMessageBotApiMethodTubeSubscriber);
        return maybeNext;
    }
}
