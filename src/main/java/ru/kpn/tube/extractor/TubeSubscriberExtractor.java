package ru.kpn.tube.extractor;

import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

public interface TubeSubscriberExtractor<T, R> {
    Optional<TubeSubscriber<T,R>> getNext();
}
