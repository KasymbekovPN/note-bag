package ru.kpn.bot;

import ru.kpn.tube.subscriber.TubeSubscriber;

public interface Publisher<T, R> {
    void subscribe(TubeSubscriber<T, R> subscriber);
}
