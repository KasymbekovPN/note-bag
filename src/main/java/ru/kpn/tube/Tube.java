package ru.kpn.tube;

import ru.kpn.tube.subscriber.TubeSubscriber;

public interface Tube<T> {
    default boolean subscribe(TubeSubscriber<T> subscriber) {return false;}
    default void append(T update){}
}
