package ru.kpn.objectFactory.creator;

// TODO: 04.12.2021 move method to Creator<>
public interface CreatorWithType<D, T, RT, S> extends Creator<D, RT, S> {
    T getType();
}
