package ru.kpn.tube;

public interface Tube<T> {
    default boolean append(T update){return false;}
}
