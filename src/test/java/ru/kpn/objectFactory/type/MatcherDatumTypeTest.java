package ru.kpn.objectFactory.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherDatumTypeTest {

    @Test
    void shouldCheckCreation() {
        for (MatcherDatumType.ALLOWED_TYPE allowedALLOWEDType : MatcherDatumType.ALLOWED_TYPE.values()) {
            MatcherDatumType type = new MatcherDatumType(allowedALLOWEDType.name());
            assertThat(type.isValid()).isTrue();
        }
    }

    @Test
    void shouldCheckCreationAttemptWithWrongType() {
        MatcherDatumType type = new MatcherDatumType("");
        assertThat(type.isValid()).isFalse();
    }
}
