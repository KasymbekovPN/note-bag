package ru.kpn.subscriber;

import java.util.Optional;

// TODO: 22.09.2021 make it comparable
public interface Subscriber<T, R>{
    default Subscriber<T, R> setNext(Subscriber<T, R> next){return null;}
    default Optional<Subscriber<T, R>> getNext(){return Optional.empty();}
    default Integer getPriority() {return -1;}
    default Optional<R> executeStrategy(T value){return Optional.empty();}
}
