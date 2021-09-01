package ru.kpn.subscriber;

import java.util.Optional;

public interface Subscriber<T, R>{
    default Subscriber<T, R> setNext(Subscriber<T, R> next){return null;}
    default Optional<Subscriber<T, R>> getNext(){return Optional.empty();}
    default Integer getPriority() {return -1;}
    default Optional<R> executeStrategy(T value){return Optional.empty();}
}
