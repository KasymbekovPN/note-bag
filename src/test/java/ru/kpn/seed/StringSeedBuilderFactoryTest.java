package ru.kpn.seed;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSeedBuilderFactoryTest {

    @Test
    void shouldCheckTakenBuilder() {
        SeedBuilder<String> builder = StringSeedBuilderFactory.builder();
        assertThat(builder.getClass()).isEqualTo(StringSeedBuilder.class);
    }
}
