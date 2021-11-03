package ru.kpn.config.extractor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.extractor.ExtractorFactoryImpl;
import ru.kpn.extractor.ExtractorType;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExtractorFactoryConfigTest {

    @Autowired
    private ExtractorFactoryImpl factory;

    @Test
    void shouldCheckByPrefixExtractorCreation() {
        Function<Update, String> extractor = factory.create(ExtractorType.BY_PREFIX, "prefix0", "prefix1");
        assertThat(ByPrefixExtractor.class).isEqualTo(extractor.getClass());
    }
}