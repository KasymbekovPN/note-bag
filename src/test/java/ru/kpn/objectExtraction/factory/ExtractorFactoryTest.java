package ru.kpn.objectExtraction.factory;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.exception.RawMessageException;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ExtractorFactoryTest {

    private ObjectFactory<ExtractorFactory.Type, Function<Update, String>> factory;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        ExtractorFactory.Builder builder = ExtractorFactory.builder();
        for (ExtractorFactory.Type type : ExtractorFactory.Type.values()) {
            builder.creator(type, new TestCreator(type));
        }
        factory = builder.build();
    }

    @Test
    void shouldCheckNotCompletelyBuilding() {
        Throwable throwable = catchThrowable(() -> {
            ExtractorFactory.builder().build();
        });
        assertThat(throwable).isInstanceOf(RawMessageException.class);
    }

    @Test
    void shouldCheckByPrefixesExtractorCreation() {
        Function<Update, String> extractor = factory.create(ExtractorFactory.Type.BY_PREFIX);
        assertThat(new TestExtractor(ExtractorFactory.Type.BY_PREFIX)).isEqualTo(extractor);
    }

    @AllArgsConstructor
    private static class TestCreator implements Function<Object[], Function<Update, String>>{

        private final ExtractorFactory.Type type;

        @Override
        public Function<Update, String> apply(Object[] objects) {
            return new TestExtractor(type);
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class TestExtractor implements Function<Update, String>{
        private final ExtractorFactory.Type type;

        @Override
        public String apply(Update update) {
            return null;
        }
    }
}
