package ru.kpn.objectFactory.factory;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderService;
import utils.USeedBuilderService;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherFactoryTest {

    private final EnumMap<MatcherDatumType.ALLOWED_TYPE, Integer> expectedValues
            = new EnumMap<>(MatcherDatumType.ALLOWED_TYPE.class);
    private ObjectFactory<MatcherDatum, Function<Update, Boolean>, Seed<String>> factory;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        Map<MatcherDatumType, TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>>> creators = new HashMap<>();
        int value = 0;
        for (MatcherDatumType.ALLOWED_TYPE allowedType : MatcherDatumType.ALLOWED_TYPE.values()) {
            creators.put(new MatcherDatumType(allowedType.name()), new TestCreator(value, allowedType));
            expectedValues.put(allowedType, value++);
        }
        factory = new MatcherFactory(creators, new StringSeedBuilderService());
    }

    @Test
    void shouldCheckCreation() {
        for (MatcherDatumType.ALLOWED_TYPE allowedType : MatcherDatumType.ALLOWED_TYPE.values()) {
            MatcherDatum datum = new MatcherDatum();
            datum.setType(allowedType.name());
            Result<Function<Update, Boolean>, Seed<String>> result = factory.create(datum);
            assertThat(new TestExtractor(expectedValues.get(allowedType))).isEqualTo(result.getValue());
        }
    }

    @Test
    void shouldCheckCreationAttemptWithWrongType() {
        String wrong = "WRONG";
        final Seed<String> expectedStatus = USeedBuilderService.takeNew().code("matcherFactory.wrongType").arg(wrong).build();
        MatcherDatum datum = new MatcherDatum();
        datum.setType(wrong);
        final Result<Function<Update, Boolean>, Seed<String>> result = factory.create(datum);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @AllArgsConstructor
    @Getter
    private static class TestCreator implements TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>> {
        private final int value;
        private final MatcherDatumType.ALLOWED_TYPE type;

        @Override
        public MatcherDatumType getType() {
            return new MatcherDatumType(type.name());
        }

        @Override
        public Result<Function<Update, Boolean>, Seed<String>> create(MatcherDatum datum) {
            return new ValuedResult<>(new TestExtractor(value));
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class TestExtractor implements Function<Update, Boolean>{
        private final int value;

        @Override
        public Boolean apply(Update update) {
            return null;
        }
    }
}
