package ru.kpn.config.extractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ExtractorFactory;
import ru.kpn.extractor.ExtractorType;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ExtractorConfig {

    @Autowired
    private ExtractorFactory<Update, String> factory;

    // TODO: 03.11.2021 del
    @Bean
    @Qualifier("simpleNoteExtractorOld")
    public Function<Update, String> simpleNodeExtractorOld(
            @Value("${telegram.tube.extractorInitData.simpleNote.prefixes}") Set<String> prefixes){
        Set<String> preparedPrefixes = preparePrefixes(prefixes);
        return factory.create(ExtractorType.BY_PREFIX, preparedPrefixes.toArray());
    }

    private Set<String> preparePrefixes(Set<String> prefixes) {
        return prefixes.stream().map((s) -> s + " ").collect(Collectors.toSet());
    }
}
