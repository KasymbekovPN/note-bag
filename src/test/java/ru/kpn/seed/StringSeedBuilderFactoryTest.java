package ru.kpn.seed;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSeedBuilderFactoryTest {

    private final StringSeedBuilderFactory factory = new StringSeedBuilderFactory();

    @Test
    void shouldCheckTakenBuilder() {
        SeedBuilder<String> builder = factory.takeBuilder();
        assertThat(builder.getClass()).isEqualTo(StringSeedBuilder.class);
    }
}
