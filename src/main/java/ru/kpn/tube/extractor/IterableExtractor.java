package ru.kpn.tube.extractor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.subscriber.Subscriber;

import java.util.Optional;

public class IterableExtractor implements SubscriberExtractor<Update, BotApiMethod<?>> {

    private Subscriber<Update, BotApiMethod<?>> subscriber;

    public IterableExtractor(Subscriber<Update, BotApiMethod<?>> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public Optional<Subscriber<Update, BotApiMethod<?>>> getNext() {
        if (subscriber != null){
            Optional<Subscriber<Update, BotApiMethod<?>>> result = Optional.of(this.subscriber);
            Optional<Subscriber<Update, BotApiMethod<?>>> maybeNext = subscriber.getNext();
            subscriber = maybeNext.orElse(null);

            return result;
        } else {
            return Optional.empty();
        }
    }
}
