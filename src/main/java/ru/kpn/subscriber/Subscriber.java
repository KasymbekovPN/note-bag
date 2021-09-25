package ru.kpn.subscriber;

import java.util.Optional;

public interface Subscriber<T, R> extends Comparable<Subscriber<T, R>>{
    default Optional<R> executeStrategy(T value){return Optional.empty();}
    default Integer getPriority() {return -1;}
}
