package ru.kpn.tube;

import ru.kpn.tube.runner.TubeRunner;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Tube<T, R> {
    default TubeRunner getRunner(){return null;}
    default void subscribe(TubeSubscriber<T, R> subscriber) {}
    default boolean append(T update){return false;}
}
