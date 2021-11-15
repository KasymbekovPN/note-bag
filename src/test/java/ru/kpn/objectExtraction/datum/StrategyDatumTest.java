package ru.kpn.objectExtraction.datum;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyDatumTest {

    private static final Integer PRIORITY = 123;

    @Test
    void shouldCheckPriority() {
        StrategyDatum datum = new StrategyDatum();
        assertThat(datum.getPriority()).isNull();
        datum.setPriority(PRIORITY);
        assertThat(datum.getPriority()).isEqualTo(PRIORITY);
    }
}
