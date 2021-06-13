package ru.kpn.tube;

import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Tube<T> {
    default void subscribe(TubeSubscriber<T> subscriber) {}
    default boolean append(T update){return false;}
    default AtomicBoolean isRun(){return new AtomicBoolean(false);}
    default void stop(){}
    default void start(){}
}
