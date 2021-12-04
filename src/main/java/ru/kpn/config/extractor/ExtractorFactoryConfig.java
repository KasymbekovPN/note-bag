package ru.kpn.config.extractor;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.extractor.ExtractorFactory;
import ru.kpn.extractor.ExtractorFactoryImpl;
import ru.kpn.extractor.ExtractorType;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


// TODO: 04.12.2021 del
@Configuration
public class ExtractorFactoryConfig {

    @Bean
    public ExtractorFactory<Update, String> extractorFactory(){
        try{
            return createFactory();
        } catch (Exception ex){
            throw new BeanCreationException(ex.getMessage());
        }
    }

    private ExtractorFactory<Update, String> createFactory() throws Exception {
        return ExtractorFactoryImpl.builder()
                .creator(ExtractorType.BY_PREFIX, new ByPrefixExtractorCreator())
                .build();
    }

    private static class ByPrefixExtractorCreator implements Function<Object[], Function<Update, String>> {
        @Override
        public Function<Update, String> apply(Object... args) {
            Set<String> prefixes = Arrays.stream(args).map(String::valueOf).collect(Collectors.toSet());
            return new ByPrefixExtractor(prefixes);
        }
    }
}
