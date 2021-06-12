package ru.kpn.tube.subscriber;

public interface TubeSubscriber<T>{
    TubeSubscriber<T> hookUp(TubeSubscriber<T> next);
    void calculate(T value);
}
