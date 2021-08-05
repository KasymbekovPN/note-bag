package ru.kpn.tube.strategy;

public interface SubscriberStrategy<T> {
    boolean execute(T value);
}
