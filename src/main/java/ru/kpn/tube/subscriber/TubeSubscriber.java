package ru.kpn.tube.subscriber;

import java.util.Optional;

public interface TubeSubscriber<T, R>{
    default TubeSubscriber<T, R> setNext(TubeSubscriber<T, R> next){return null;}
    default Optional<TubeSubscriber<T, R>> getNext(){return Optional.empty();}
    default Integer getPriority() {return -1;}
    default Optional<R> executeStrategy(T value){return Optional.empty();}
}
