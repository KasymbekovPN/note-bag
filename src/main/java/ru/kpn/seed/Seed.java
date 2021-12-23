package ru.kpn.seed;

public interface Seed<T> {
    T getCode();
    Object[] getArgs();
}
