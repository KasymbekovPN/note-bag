package ru.kpn.config.extractor;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Configuration
public class ExtractorConfig {

    @Bean
    @Qualifier("simpleNoteExtractor")
    public Function<Update, String> simpleNodeExtractor(
            @Value("${telegram.tube.strategies.simpleNoteStrategy.templates}") List<String> templates){
        return new ByPrefixExtractor(getPrefixes(templates));        
    }

    private List<String> getPrefixes(List<String> templates) {
        if (templates.size() == 0){
            // TODO: 23.10.2021 translate it
            throw new BeanCreationException("Templates for extractor creation is empty");
        }
        ArrayList<String> prefixes = new ArrayList<>();
        for (String template : templates) {
            String[] splitTemplate = template.split("\\.");
            if (splitTemplate.length != 2){
                // TODO: 23.10.2021 translate it
                throw new BeanCreationException("Wrong template : " + template);
            }
            prefixes.add(splitTemplate[0]);
        }
        return prefixes;
    }
}
