package ru.kpn.creator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StrategyInitCreatorTest {

    private static final int PRIORITY = 123;
    private static final String STRATEGY_NAME = "strategyName";
    private static final String WRONG_STRATEGY_NAME = "wrongStrategyName";

    private StrategyInitCreator creator;
    private RawMessage<String> expectedRawMessage;

    @BeforeEach
    void setUp() {
        StrategyInitCreator.Datum datum = new StrategyInitCreator.Datum();
        datum.setPriority(PRIORITY);
        Map<String, StrategyInitCreator.Datum> strategyInitData = new HashMap<>(){{
            put(STRATEGY_NAME, datum);
        }};
        creator = new StrategyInitCreator();
        creator.setStrategyInitData(strategyInitData);

        expectedRawMessage = new BotRawMessage("priority.notSet.for").add(WRONG_STRATEGY_NAME);
    }

    @Test
    void shouldCheckDatumByStrategyName() {
        StrategyInitCreator.Result result = creator.getDatum(STRATEGY_NAME);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getPriority()).isEqualTo(PRIORITY);
    }

    @Test
    void shouldCheckDatumByWrongStrategyName() {
        StrategyInitCreator.Result result = creator.getDatum(WRONG_STRATEGY_NAME);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
    }
}