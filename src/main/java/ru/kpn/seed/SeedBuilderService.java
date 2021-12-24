package ru.kpn.seed;

public interface SeedBuilderService<T> {
    SeedBuilder<T> takeNew();
}
