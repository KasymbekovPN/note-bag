package ru.kpn.objectExtraction.datum;

import org.junit.jupiter.api.Test;
import ru.kpn.objectExtraction.type.MatcherDatumType;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MatcherDatumTest {

    @Test
    void shouldCheckTypeGettingSetting() {
        for (MatcherDatumType.ALLOWED_TYPE allowedType : MatcherDatumType.ALLOWED_TYPE.values()) {
            String typeName = allowedType.name();
            MatcherDatum datum = new MatcherDatum();
            datum.setType(typeName);
            assertThat(new MatcherDatumType(typeName)).isEqualTo(datum.getType());
        }
    }

    @Test
    void shouldCheckConstantSettingGetting() {
        MatcherDatum datum = new MatcherDatum();
        boolean constantValue = true;
        datum.setConstant(constantValue);
        assertThat(constantValue).isEqualTo(datum.getConstant());
    }

    @Test
    void shouldCheckTemplateSettingGetting() {
        MatcherDatum datum = new MatcherDatum();
        String template = "template";
        datum.setTemplate(template);
        assertThat(template).isEqualTo(datum.getTemplate());
    }

    @Test
    void shouldCheckTemplatesSettingGetting() {
        MatcherDatum datum = new MatcherDatum();
        Set<String> templates = Set.of("t0", "t1");
        datum.setTemplates(templates);
        assertThat(templates).isEqualTo(datum.getTemplates());
    }
}