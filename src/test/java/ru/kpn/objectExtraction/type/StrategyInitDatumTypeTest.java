package ru.kpn.objectExtraction.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyInitDatumTypeTest {

    @Test
    void shouldCheckCreation() {
        StrategyInitDatumType type = new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name());
        assertThat(type.isValid()).isTrue();
    }

    @Test
    void shouldCheckCreationAttemptWithWrongType() {
        StrategyInitDatumType type = new StrategyInitDatumType("");
        assertThat(type.isValid()).isFalse();
    }
}
