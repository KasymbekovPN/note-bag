package ru.kpn.statusSeed.service;

import ru.kpn.statusSeed.builder.StatusSeedBuilder;

public interface StatusSeedBuilderService<T> {
    StatusSeedBuilder<T> takeBuilder();
}
