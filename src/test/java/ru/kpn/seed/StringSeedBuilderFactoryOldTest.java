package ru.kpn.seed;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSeedBuilderFactoryOldTest {

    @Test
    void shouldCheckTakenBuilder() {
        SeedBuilder<String> builder = StringSeedBuilderFactoryOld.builder();
        assertThat(builder.getClass()).isEqualTo(StringSeedBuilder.class);
    }
}
