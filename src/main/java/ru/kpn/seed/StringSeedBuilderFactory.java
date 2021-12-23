package ru.kpn.seed;

public class StringSeedBuilderFactory {
    public static SeedBuilder<String> builder() {
        return new StringSeedBuilder();
    }
}
