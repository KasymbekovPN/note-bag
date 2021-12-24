package ru.kpn.seed;

import org.springframework.stereotype.Service;

@Service
public class StringSeedBuilderService implements SeedBuilderService<String> {
    @Override
    public SeedBuilder<String> takeNew() {
        return new StringSeedBuilder();
    }
}
