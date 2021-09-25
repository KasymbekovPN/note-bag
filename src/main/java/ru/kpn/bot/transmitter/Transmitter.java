package ru.kpn.bot.transmitter;

public interface Transmitter<T> {
    void transmit(T value);
}
