package ru.kpn.config.extractor;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExtractorConfigTest {

    // TODO: 06.11.2021 del 
    @Autowired
    @Qualifier("simpleNoteExtractorOld")
    private Function<Update, String> simpleNoteExtractorOld;

    // TODO: 06.11.2021 del
    @Test
    void shouldCheckSimpleNoteExtractorOld() {
        final Set<String> expectedPrefixes = Set.of("/sn ", "/simple note ");
        final Set<String> prefixes = getPrefixes(simpleNoteExtractorOld);
        assertThat(expectedPrefixes).isEqualTo(prefixes);
    }

    @SneakyThrows
    private Set<String> getPrefixes(Function<Update, String> instance) {
        Field field = ByPrefixExtractor.class.getDeclaredField("prefixes");
        field.setAccessible(true);
        return (Set<String>) ReflectionUtils.getField(field, instance);
    }
}
