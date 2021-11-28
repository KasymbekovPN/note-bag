package ru.kpn.objectExtraction.datum;

import org.junit.jupiter.api.Test;
import ru.kpn.objectExtraction.type.StrategyInitDatumType;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyInitDatumTest {

    @Test
    void shouldCheckTypeSettingGetting() {
        StrategyInitDatum datum = new StrategyInitDatum();
        String typeName = StrategyInitDatumType.ALLOWED_TYPE.COMMON.name();
        datum.setType(typeName);
        assertThat(new StrategyInitDatumType(typeName)).isEqualTo(datum.getType());
    }

    @Test
    void shouldCheckPrioritySettingGetting() {
        StrategyInitDatum datum = new StrategyInitDatum();
        int priority = 12;
        datum.setPriority(priority);
        datum.setPriority(priority);
        assertThat(priority).isEqualTo(datum.getPriority());
    }
}
