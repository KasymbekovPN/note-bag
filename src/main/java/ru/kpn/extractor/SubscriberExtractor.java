package ru.kpn.tube.extractor;

import ru.kpn.tube.subscriber.Subscriber;

import java.util.Optional;

public interface SubscriberExtractor<T, R> {
    Optional<Subscriber<T,R>> getNext();
}
