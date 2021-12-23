package ru.kpn.seed;

public interface SeedBuilder<T> {
    SeedBuilder<T> code(String code);
    SeedBuilder<T> arg(Object arg);
    Seed<T> build();
}
