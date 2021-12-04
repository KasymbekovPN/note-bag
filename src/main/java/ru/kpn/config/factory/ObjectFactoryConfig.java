package ru.kpn.config.factory;

import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.creator.CreatorWithType;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.datum.StrategyInitDatum;
import ru.kpn.objectExtraction.factory.ExtractorFactory;
import ru.kpn.objectExtraction.factory.MatcherFactory;
import ru.kpn.objectExtraction.factory.StrategyInitFactory;
import ru.kpn.objectExtraction.type.ExtractorDatumType;
import ru.kpn.objectExtraction.type.MatcherDatumType;
import ru.kpn.objectExtraction.type.StrategyInitDatumType;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.rawMessage.RawMessage;

import java.util.List;
import java.util.function.Function;

@Configuration
public class ObjectFactoryConfig {

    @SneakyThrows
    @Bean
    public ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> strategyInitFactory(List<CreatorWithType<StrategyInitDatum, StrategyInitDatumType, Integer, RawMessage<String>>> creators){
        StrategyInitFactory.Builder builder = StrategyInitFactory.builder();
        for (CreatorWithType<StrategyInitDatum, StrategyInitDatumType, Integer, RawMessage<String>> creator : creators) {
            StrategyInitDatumType type = creator.getType();
            if (type.isValid()){
                builder.creator(type, creator);
            } else {
                throw new BeanCreationException(String.format("Invalid type: %s", type.asStr()));
            }
        }
        return builder.build();
    }

    @SneakyThrows
    @Bean
    public ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> extractorFactory(
            List<CreatorWithType<ExtractorDatum, ExtractorDatumType, Function<Update, String>, RawMessage<String>>> creators
    ){
        ExtractorFactory.Builder builder = ExtractorFactory.builder();
        for (CreatorWithType<ExtractorDatum, ExtractorDatumType, Function<Update, String>, RawMessage<String>> creator : creators) {
            ExtractorDatumType type = creator.getType();
            if (type.isValid()){
                builder.creator(type, creator);
            } else {
                throw new BeanCreationException(String.format("Invalid type: %s", type.asStr()));
            }
        }
        return builder.build();
    }

    @SneakyThrows
    @Bean
    public ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessage<String>> matcherFactory(
            List<CreatorWithType<MatcherDatum, MatcherDatumType, Function<Update, Boolean>, RawMessage<String>>> creators
    ){
        MatcherFactory.Builder builder = MatcherFactory.builder();
        for (CreatorWithType<MatcherDatum, MatcherDatumType, Function<Update, Boolean>, RawMessage<String>> creator : creators) {
            MatcherDatumType type = creator.getType();
            if (type.isValid()){
                builder.creator(type, creator);
            } else {
                throw new BeanCreationException(String.format("Invalid type: %s", type.asStr()));
            }
        }
        return builder.build();
    }
}
