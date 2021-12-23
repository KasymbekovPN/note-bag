package ru.kpn.statusSeed.service;

import org.springframework.stereotype.Service;
import ru.kpn.statusSeed.builder.StatusSeedBuilder;
import ru.kpn.statusSeed.builder.StringStatusSeedBuilder;

@Service
public class StringStatusSeedBuilderService implements StatusSeedBuilderService<String> {
    @Override
    public StatusSeedBuilder<String> takeBuilder() {
        return new StringStatusSeedBuilder();
    }
}
