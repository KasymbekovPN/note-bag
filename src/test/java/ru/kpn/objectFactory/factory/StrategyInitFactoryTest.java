package ru.kpn.objectFactory.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.OptimisticResult;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

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
            builder.creator(new StrategyInitDatumType(allowedType.name()), new TestCreator(value));
            expectedValues.put(allowedType, value++);
        }
        factory = builder.build();
    }

    @Test
    void shouldCheckAttemptOfNotCompletelyCreationOfFactory() {
        Throwable throwable = catchThrowable(() -> {
            StrategyInitFactory.builder().build();
        });
        assertThat(throwable).isInstanceOf(Exception.class);
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
    private static class TestCreator implements Creator<StrategyInitDatum, Integer, RawMessage<String>> {
        private final int value;

        @Override
        public Result<Integer, RawMessage<String>> create(StrategyInitDatum datum) {
            return OptimisticResult.<Integer>builder().value(value).build();
        }
    }
}
