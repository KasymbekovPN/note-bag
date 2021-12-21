package ru.kpn.objectFactory.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyInitFactoryTest {

    private final EnumMap<StrategyInitDatumType.ALLOWED_TYPE, Integer> expectedValues
            = new EnumMap<>(StrategyInitDatumType.ALLOWED_TYPE.class);
    private final RawMessageFactory<String> messageFactory = new BotRawMessageFactory();
    private ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> factory;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        StrategyInitFactory.Builder builder = StrategyInitFactory.builder();
        int value = 0;
        for (StrategyInitDatumType.ALLOWED_TYPE allowedType : StrategyInitDatumType.ALLOWED_TYPE.values()) {
            builder.create(new TestCreator(value));
            expectedValues.put(allowedType, value++);
        }
        factory = builder.check().calculateValue().buildResult().getValue();
    }

    @Test
    void shouldCheckAttemptOfNotCompletelyCreationOfFactory() {
        BotRawMessage expectedStatus = new BotRawMessage("notCompletely.creators.strategyInit");
        Result<ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>>, RawMessage<String>> result
                = StrategyInitFactory.builder().check().calculateValue().buildResult();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckCreation() {
        for (StrategyInitDatumType.ALLOWED_TYPE allowedType : StrategyInitDatumType.ALLOWED_TYPE.values()) {
            StrategyInitDatum datum = new StrategyInitDatum();
            datum.setType(allowedType.name());
            Result<Integer, RawMessage<String>> result = factory.create(datum);
            assertThat(expectedValues.get(allowedType)).isEqualTo(result.getValue());
        }
    }

    @Test
    void shouldCheckCreationAttemptWithWrongType() {
        String wrong = "WRONG";
        RawMessage<String> expectedStatus = messageFactory.create("strategyInitFactory.wrongType").add(wrong);
        StrategyInitDatum datum = new StrategyInitDatum();
        datum.setType(wrong);
        Result<Integer, RawMessage<String>> result = factory.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @AllArgsConstructor
    @Getter
    private static class TestCreator implements TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, RawMessage<String>> {
        private final int value;

        @Override
        public StrategyInitDatumType getType() {
            return new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name());
        }

        @Override
        public Result<Integer, RawMessage<String>> create(StrategyInitDatum datum) {
            return new ValuedResult<>(value);
        }
    }
}
