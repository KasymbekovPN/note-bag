package ru.kpn.matcher;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class MatcherFactoryImplTest {
    private MatcherFactoryImpl factory;
    
    @SneakyThrows
    @BeforeEach
    void setUp() {
        MatcherFactoryImpl.Builder builder = MatcherFactoryImpl.builder();
        for (MatcherType type : MatcherType.values()) {
            builder.creator(type, new TestMatcherCreator(String.valueOf(type)));
        }
        factory = builder.build();
    }

    @Test
    void shouldCheckCreation() {
        for (MatcherType type : MatcherType.values()) {
            TestMatcher matcher = (TestMatcher) factory.create(type);
            assertThat(String.valueOf(type)).isEqualTo(matcher.getType());
        }
    }

    @Test
    void shouldCheckInvalidFactoryBuilding() {
        Throwable throwable = catchThrowable(() -> {
            MatcherFactoryImpl factory = MatcherFactoryImpl.builder().build();
        });
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @AllArgsConstructor
    private static class TestMatcherCreator implements Function<Object[], Function<Update, Boolean>> {
        private final String type;

        @Override
        public Function<Update, Boolean> apply(Object[] objects) {
            return new TestMatcher(type);
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    private static class TestMatcher implements Function<Update, Boolean> {
        private final String type;

        @Override
        public Boolean apply(Update update) {
            return null;
        }
    }
}
