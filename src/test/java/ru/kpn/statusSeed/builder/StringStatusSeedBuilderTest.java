package ru.kpn.statusSeed.builder;

import org.junit.jupiter.api.Test;
import ru.kpn.statusSeed.seed.StatusSeed;
import ru.kpn.statusSeed.seed.StringStatusSeed;

import static org.assertj.core.api.Assertions.assertThat;

public class StringStatusSeedBuilderTest {

    @Test
    void shouldCheckStringStatusSeedBuilding() {
        String expectedCode = "code";
        Object[] expectedArgs = {1, 2, 3};
        StatusSeedBuilder<String> builder = new StringStatusSeedBuilder().code(expectedCode);
        for (Object expectedArg : expectedArgs) {
            builder.arg(expectedArg);
        }
        StatusSeed<String> statusSeed = builder.build();
        assertThat(statusSeed).isEqualTo(new StringStatusSeed(expectedCode, expectedArgs));
    }
}
