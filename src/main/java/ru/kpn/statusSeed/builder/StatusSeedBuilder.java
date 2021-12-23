package ru.kpn.statusSeed.builder;

import ru.kpn.statusSeed.seed.StatusSeed;

public interface StatusSeedBuilder<T> {
    StatusSeedBuilder<T> arg(Object arg);
    StatusSeed<T> build();
}
