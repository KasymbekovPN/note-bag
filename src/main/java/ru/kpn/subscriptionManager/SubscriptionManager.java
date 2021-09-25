package ru.kpn.subscriptionManager;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.subscriber.Subscriber;

public interface SubscriptionManager<T, R> {
    void subscribe(Subscriber<T, R> subscriber);
    BotApiMethod<?> execute(Update update);
}
