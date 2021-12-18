package ru.kpn.objectFactory.creator;

public interface CreatorWithType<D, T, RT, S> extends Creator<D, RT, S> {
    T getType();
}
