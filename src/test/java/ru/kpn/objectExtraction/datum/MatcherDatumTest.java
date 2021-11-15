package ru.kpn.objectExtraction.datum;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherDatumTest {

    private static final String TYPE = "type";
    private static final Boolean CONSTANT = true;
    private static final String TEMPLATE = "template";
    private static final Set<String> TEMPLATES = Set.of("t0", "t1");

    @Test
    void shouldCheckType() {
        MatcherDatum result = new MatcherDatum();
        assertThat(result.getType()).isNull();
        result.setType(TYPE);
        assertThat(result.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldCheckConstant() {
        MatcherDatum result = new MatcherDatum();
        assertThat(result.getConstant()).isNull();
        result.setConstant(CONSTANT);
        assertThat(result.getConstant()).isEqualTo(CONSTANT);
    }

    @Test
    void shouldCheckTemplate() {
        MatcherDatum result = new MatcherDatum();
        assertThat(result.getTemplate()).isNull();
        result.setTemplate(TEMPLATE);
        assertThat(result.getTemplate()).isEqualTo(TEMPLATE);
    }

    @Test
    void shouldCheckTemplates() {
        MatcherDatum result = new MatcherDatum();
        assertThat(result.getTemplates()).isNull();
        result.setTemplates(TEMPLATES);
        assertThat(result.getTemplates()).isEqualTo(TEMPLATES);
    }
}
