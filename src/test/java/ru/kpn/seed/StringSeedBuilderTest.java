package ru.kpn.seed;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSeedBuilderTest {

    @Test
    void shouldCheckStringStatusSeedBuilding() {
        String expectedCode = "code";
        Object[] expectedArgs = {1, 2, 3};
        SeedBuilder<String> builder = new StringSeedBuilder().code(expectedCode);
        for (Object expectedArg : expectedArgs) {
            builder.arg(expectedArg);
        }
        Seed<String> seed = builder.build();
        assertThat(seed).isEqualTo(new StringSeed(expectedCode, expectedArgs));
    }
}
