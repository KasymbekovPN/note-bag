package ru.kpn.seed;

public class StringSeedBuilderFactory implements SeedBuilderService<String> {
    @Override
    public SeedBuilder<String> takeBuilder() {
        return new StringSeedBuilder();
    }
}
