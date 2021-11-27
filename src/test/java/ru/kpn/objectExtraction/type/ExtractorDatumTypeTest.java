package ru.kpn.objectExtraction.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtractorDatumTypeTest {

    @Test
    void shouldCheckCreation() {
        ExtractorDatumType type = new ExtractorDatumType(ExtractorDatumType.ALLOWED_TYPE.BY_PREFIX.name());
        assertThat(type.isValid()).isTrue();
    }

    @Test
    void shouldCheckCreationAttemptWithWrongType() {
        ExtractorDatumType type = new ExtractorDatumType("");
        assertThat(type.isValid()).isFalse();
    }
}
