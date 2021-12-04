package ru.kpn.objectFactory.datum;

import org.junit.jupiter.api.Test;
import ru.kpn.objectFactory.type.ExtractorDatumType;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractorDatumTest {

    @Test
    void shouldCheckTypeSettingGetting() {
        ExtractorDatum datum = new ExtractorDatum();
        String typeName = ExtractorDatumType.ALLOWED_TYPE.BY_PREFIX.name();
        datum.setType(typeName);
        ExtractorDatumType type = datum.getType();
        assertThat(new ExtractorDatumType(typeName)).isEqualTo(type);
    }

    @Test
    void shouldCheckPrefixesSettingGetting() {
        ExtractorDatum datum = new ExtractorDatum();
        Set<String> prefixes = Set.of("p0", "p1");
        datum.setPrefixes(prefixes);
        assertThat(prefixes).isEqualTo(datum.getPrefixes());
    }
}