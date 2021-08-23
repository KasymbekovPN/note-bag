package ru.kpn.tube.extractor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

public class TubeSubscriberExtractorImpl implements TubeSubscriberExtractor<Update, BotApiMethod<?>> {

    private TubeSubscriber<Update, BotApiMethod<?>> tubeSubscriber;

    public TubeSubscriberExtractorImpl(TubeSubscriber<Update, BotApiMethod<?>> tubeSubscriber) {
        this.tubeSubscriber = tubeSubscriber;
    }

    @Override
    public Optional<TubeSubscriber<Update, BotApiMethod<?>>> getNext() {
        Optional<TubeSubscriber<Update, BotApiMethod<?>>> maybeNext = tubeSubscriber.getNext();
        maybeNext.ifPresent(tubeMessageBotApiMethodTubeSubscriber -> tubeSubscriber = tubeMessageBotApiMethodTubeSubscriber);
        return maybeNext;
    }
}
