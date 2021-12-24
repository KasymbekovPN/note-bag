package ru.kpn.objectFactory.creator.strategyInit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.seed.Seed;
import utils.USeedBuilderService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StrategyInitCreatorTest {

    private static final String KEY = StrategyInitCreator.class.getSimpleName();
    private static final int PRIORITY = 1;

    @Autowired
    private StrategyInitCreator creator;

    @Test
    void shouldCheckCreationAttemptWhenDatumIsNull() {
        final Seed<String> expectedMessage = USeedBuilderService.takeNew().code("datum.isNull").arg(KEY).build();
        Result<Integer, Seed<String>> result = creator.create(null);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreationAttemptWhenPriorityIsNull() {
        final Seed<String> expectedMessage = USeedBuilderService.takeNew().code("datum.priority.isNull").arg(KEY).build();
        Result<Integer, Seed<String>> result = creator.create(new StrategyInitDatum());
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCheckCreation() {
        StrategyInitDatum datum = new StrategyInitDatum();
        datum.setPriority(PRIORITY);
        Result<Integer, Seed<String>> result = creator.create(datum);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(PRIORITY);
    }

    @Test
    void shouldCheckGottenType() {
        StrategyInitDatumType type = new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name());
        assertThat(type).isEqualTo(creator.getType());
    }
}
