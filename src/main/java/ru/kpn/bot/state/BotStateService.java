package ru.kpn.bot;

public interface BotStateService<T, R> {
    R get(T value);
    void set(T value, R state);
    void remove(T value);
}
