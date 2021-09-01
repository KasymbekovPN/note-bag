package ru.kpn.bot.publisher;

import ru.kpn.subscriber.Subscriber;

public interface Publisher<T, R> {
    void subscribe(Subscriber<T, R> subscriber);
}
