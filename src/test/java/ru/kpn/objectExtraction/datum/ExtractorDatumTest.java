package ru.kpn.objectExtraction.datum;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtractorDatumTest {

    private static final String TYPE = "type";
    private static final Set<String> PREFIXES = Set.of("p0", "p1");

    @Test
    void shouldCheckType() {
        ExtractorDatum datum = new ExtractorDatum();
        assertThat(datum.getType()).isNull();
        datum.setType(TYPE);
        assertThat(datum.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldCheckPrefixes() {
        ExtractorDatum datum = new ExtractorDatum();
        assertThat(datum.getPrefixes()).isNull();
        datum.setPrefixes(PREFIXES);
        assertThat(datum.getPrefixes()).isEqualTo(PREFIXES);
    }
}
