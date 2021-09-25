package ru.kpn.subscriptionManager;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.subscriber.Subscriber;

public interface SubscriptionManager<T, R> {
    void subscribe(Subscriber<T, R> subscriber);
    void execute(Update update);
}
