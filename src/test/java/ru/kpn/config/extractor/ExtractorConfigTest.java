package ru.kpn.config.extractor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExtractorConfigTest {

    @Autowired
    @Qualifier("simpleNoteExtractor")
    private Function<Update, String> simpleNoteExtractor;

    @Test
    void shouldCheckSimpleNodeExtractor() {
        ByPrefixExtractor expectedExtractor = new ByPrefixExtractor(List.of("/simple note ", "/sn "));
        assertThat(expectedExtractor).isEqualTo(simpleNoteExtractor);
    }
}
