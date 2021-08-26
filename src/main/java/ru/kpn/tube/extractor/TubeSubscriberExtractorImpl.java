package ru.kpn.tube.extractor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

// TODO: 25.08.2021 add rename
public class TubeSubscriberExtractorImpl implements TubeSubscriberExtractor<Update, BotApiMethod<?>> {

    private TubeSubscriber<Update, BotApiMethod<?>> subscriber;

    public TubeSubscriberExtractorImpl(TubeSubscriber<Update, BotApiMethod<?>> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public Optional<TubeSubscriber<Update, BotApiMethod<?>>> getNext() {
        if (subscriber != null){
            Optional<TubeSubscriber<Update, BotApiMethod<?>>> result = Optional.of(this.subscriber);
            Optional<TubeSubscriber<Update, BotApiMethod<?>>> maybeNext = subscriber.getNext();
            subscriber = maybeNext.orElse(null);

            return result;
        } else {
            return Optional.empty();
        }
    }
}
