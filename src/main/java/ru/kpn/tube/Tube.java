package ru.kpn.tube;

import ru.kpn.tube.runner.TubeRunner;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Tube<T> {
    default TubeRunner getRunner(){return null;}
    default boolean append(T update){return false;}
}
