package ru.kpn.extractor;

import ru.kpn.subscriber.Subscriber;

import java.util.Optional;

public interface SubscriberExtractor<T, R> {
    Optional<Subscriber<T,R>> getNext();
}
