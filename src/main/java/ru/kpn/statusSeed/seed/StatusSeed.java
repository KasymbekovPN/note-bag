package ru.kpn.statusSeed.seed;

public interface StatusSeed<T> {
    T getCode();
    Object[] getArgs();
}
