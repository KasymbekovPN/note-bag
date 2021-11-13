package ru.kpn.creator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StrategyInitCreatorTest {

    private static final int PRIORITY = 123;
    private static final String STRATEGY_NAME = "strategyName";
    private static final String WRONG_STRATEGY_NAME = "wrongStrategyName";

    private final RawMessageFactory<String> rawMessageFactory = new BotRawMessageFactory();
    private final StrategyInitCreator creator = new StrategyInitCreator();

    private RawMessage<String> expectedRawMessage;

    @BeforeEach
    void setUp() {
        StrategyInitCreator.Datum datum = new StrategyInitCreator.Datum();
        datum.setPriority(PRIORITY);
        Map<String, StrategyInitCreator.Datum> strategyInitData = new HashMap<>(){{
            put(STRATEGY_NAME, datum);
        }};
        creator.setStrategyInitData(strategyInitData);
        creator.setRawMessageFactory(rawMessageFactory);

        expectedRawMessage = rawMessageFactory.create("priority.notSet.for").add(WRONG_STRATEGY_NAME);
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