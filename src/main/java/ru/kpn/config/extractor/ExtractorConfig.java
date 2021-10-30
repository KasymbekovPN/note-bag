package ru.kpn.config.extractor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ExtractorConfig {

    @Bean
    @Qualifier("simpleNoteExtractor")
    public Function<Update, String> simpleNodeExtractor(
            @Value("${telegram.tube.contentExtractors.simpleNote.prefixes}") Set<String> prefixes){
        Set<String> preparedPrefixes = preparePrefixes(prefixes);
        return new ByPrefixExtractor(preparedPrefixes);
    }

    private Set<String> preparePrefixes(Set<String> prefixes) {
        return prefixes.stream().map((s) -> s + " ").collect(Collectors.toSet());
    }
}
