package ru.kpn.bot;

import ru.kpn.tube.subscriber.Subscriber;

public interface Publisher<T, R> {
    void subscribe(Subscriber<T, R> subscriber);
}
