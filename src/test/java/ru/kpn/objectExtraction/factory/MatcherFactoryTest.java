package ru.kpn.objectExtraction.factory;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.exception.RawMessageException;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class MatcherFactoryTest {


    private ObjectFactory<MatcherFactory.Type, Function<Update, Boolean>> factory;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        factory = MatcherFactory.builder()
                .creator(MatcherFactory.Type.CONSTANT, new TestCreator(MatcherFactory.Type.CONSTANT))
                .creator(MatcherFactory.Type.REGEX, new TestCreator(MatcherFactory.Type.REGEX))
                .creator(MatcherFactory.Type.MULTI_REGEX, new TestCreator(MatcherFactory.Type.MULTI_REGEX))
                .build();
    }

    @Test
    void shouldCheckNotCompletelyBuilding() {
        Throwable throwable = catchThrowable(() -> {
            MatcherFactory.builder().build();
        });
        assertThat(throwable).isInstanceOf(RawMessageException.class);
    }

    @Test
    void shouldCheckConstantMatcherCreation() {
        TestMatcher matcher = (TestMatcher) factory.create(MatcherFactory.Type.CONSTANT);
        assertThat(MatcherFactory.Type.CONSTANT).isEqualTo(matcher.getType());
    }

    @Test
    void shouldCheckRegexMatcherCreation() {
        TestMatcher matcher = (TestMatcher) factory.create(MatcherFactory.Type.REGEX);
        assertThat(MatcherFactory.Type.REGEX).isEqualTo(matcher.getType());
    }

    @Test
    void shouldCheckMultiMatcherCreation() {
        TestMatcher matcher = (TestMatcher) factory.create(MatcherFactory.Type.MULTI_REGEX);
        assertThat(MatcherFactory.Type.MULTI_REGEX).isEqualTo(matcher.getType());
    }

    private static class TestCreator implements Function<Object[], Function<Update, Boolean>> {
        private final MatcherFactory.Type type;

        public TestCreator(MatcherFactory.Type type) {
            this.type = type;
        }

        @Override
        public Function<Update, Boolean> apply(Object[] objects) {
            return new TestMatcher(type);
        }
    }

    private static class TestMatcher implements Function<Update, Boolean> {
        private final MatcherFactory.Type type;

        public TestMatcher(MatcherFactory.Type type) {
            this.type = type;
        }

        public MatcherFactory.Type getType() {
            return type;
        }

        @Override
        public Boolean apply(Update update) {
            return null;
        }
    }
}
