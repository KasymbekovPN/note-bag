package ru.kpn.extractor;

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

public class ExtractorFactoryImplTest {

    private ExtractorFactory<Update, String> factory;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        ExtractorFactoryImpl.Builder builder = ExtractorFactoryImpl.builder();
        for (ExtractorType type : ExtractorType.values()) {
            builder.creator(type, new TestExtractorCreator(String.valueOf(type)));
        }
        factory = builder.build();
    }

    @Test
    void shouldCheckCreation() {
        for (ExtractorType type : ExtractorType.values()) {
            TestExtractor extractor = (TestExtractor) factory.create(type);
            assertThat(String.valueOf(type)).isEqualTo(extractor.getType());
        }
    }

    @Test
    void shouldCheckInvalidFactoryBuilding() {
        Throwable throwable = catchThrowable(() -> {
            ExtractorFactoryImpl factory = ExtractorFactoryImpl.builder().build();
        });
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @AllArgsConstructor
    private static class TestExtractorCreator implements Function<Object[], Function<Update, String>> {
        private final String type;

        @Override
        public Function<Update, String> apply(Object[] objects) {
            return new TestExtractor(type);
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    private static class TestExtractor implements Function<Update, String> {
        private final String type;

        @Override
        public String apply(Update update) {
            return null;
        }
    }
}
