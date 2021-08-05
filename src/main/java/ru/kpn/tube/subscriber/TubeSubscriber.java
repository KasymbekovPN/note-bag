package ru.kpn.tube.subscriber;

public interface TubeSubscriber<T>{
    default void setNext(TubeSubscriber<T> next){}
    default TubeSubscriber<T> hookUp(TubeSubscriber<T> previous){return null;}
    default void calculate(T value){}
}
