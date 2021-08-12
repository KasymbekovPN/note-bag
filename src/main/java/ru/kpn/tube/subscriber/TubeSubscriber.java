package ru.kpn.tube.subscriber;

import java.util.Optional;

public interface TubeSubscriber<T, R>{
    default void setNext(TubeSubscriber<T, R> next){}
    default TubeSubscriber<T, R> hookUp(TubeSubscriber<T, R> previous){return null;}
    Optional<R> calculate(T value);
}
