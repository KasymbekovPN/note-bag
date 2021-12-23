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
import ru.kpn.rawMessage.BotRawMessageOld;
import ru.kpn.rawMessage.BotRawMessageFactoryOld;
import ru.kpn.rawMessage.RawMessageOld;
import ru.kpn.rawMessage.RawMessageFactoryOld;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyInitFactoryTest {

    private final EnumMap<StrategyInitDatumType.ALLOWED_TYPE, Integer> expectedValues
            = new EnumMap<>(StrategyInitDatumType.ALLOWED_TYPE.class);
    private final RawMessageFactoryOld<String> messageFactory = new BotRawMessageFactoryOld();
    private ObjectFactory<StrategyInitDatum, Integer, RawMessageOld<String>> factory;

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
        BotRawMessageOld expectedStatus = new BotRawMessageOld("notCompletely.creators.strategyInit");
        Result<ObjectFactory<StrategyInitDatum, Integer, RawMessageOld<String>>, RawMessageOld<String>> result
                = StrategyInitFactory.builder().check().calculateValue().buildResult();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckCreation() {
        for (StrategyInitDatumType.ALLOWED_TYPE allowedType : StrategyInitDatumType.ALLOWED_TYPE.values()) {
            StrategyInitDatum datum = new StrategyInitDatum();
            datum.setType(allowedType.name());
            Result<Integer, RawMessageOld<String>> result = factory.create(datum);
            assertThat(expectedValues.get(allowedType)).isEqualTo(result.getValue());
        }
    }

    @Test
    void shouldCheckCreationAttemptWithWrongType() {
        String wrong = "WRONG";
        RawMessageOld<String> expectedStatus = messageFactory.create("strategyInitFactory.wrongType").add(wrong);
        StrategyInitDatum datum = new StrategyInitDatum();
        datum.setType(wrong);
        Result<Integer, RawMessageOld<String>> result = factory.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @AllArgsConstructor
    @Getter
    private static class TestCreator implements TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, RawMessageOld<String>> {
        private final int value;

        @Override
        public StrategyInitDatumType getType() {
            return new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name());
        }

        @Override
        public Result<Integer, RawMessageOld<String>> create(StrategyInitDatum datum) {
            return new ValuedResult<>(value);
        }
    }
}
