package ru.kpn.creator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StrategyInitCreatorOldTest {

    private static final int PRIORITY = 123;
    private static final String STRATEGY_NAME = "strategyName";
    private static final String WRONG_STRATEGY_NAME = "wrongStrategyName";

    private final RawMessageFactory<String> rawMessageFactory = new BotRawMessageFactory();
    private final StrategyInitCreatorOld creator = new StrategyInitCreatorOld();

    private RawMessage<String> expectedRawMessage;

    @BeforeEach
    void setUp() {
        StrategyInitCreatorOld.Datum datum = new StrategyInitCreatorOld.Datum();
        datum.setPriority(PRIORITY);
        Map<String, StrategyInitCreatorOld.Datum> strategyInitData = new HashMap<>(){{
            put(STRATEGY_NAME, datum);
        }};
        creator.setStrategyInitData(strategyInitData);
        creator.setRawMessageFactory(rawMessageFactory);

        expectedRawMessage = rawMessageFactory.create("priority.notSet.for").add(WRONG_STRATEGY_NAME);
    }

    @Test
    void shouldCheckDatumByStrategyName() {
        StrategyInitCreatorOld.Result result = creator.getDatum(STRATEGY_NAME);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getPriority()).isEqualTo(PRIORITY);
    }

    @Test
    void shouldCheckDatumByWrongStrategyName() {
        StrategyInitCreatorOld.Result result = creator.getDatum(WRONG_STRATEGY_NAME);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
    }
}