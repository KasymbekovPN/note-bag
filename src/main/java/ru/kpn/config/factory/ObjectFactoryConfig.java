package ru.kpn.config.factory;

import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.factory.ExtractorFactory;
import ru.kpn.objectFactory.factory.MatcherFactory;
import ru.kpn.objectFactory.factory.StrategyInitFactory;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.rawMessage.RawMessage;

import java.util.List;
import java.util.function.Function;

@Configuration
public class ObjectFactoryConfig {

    @SneakyThrows
    @Bean
    public ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> strategyInitFactory(
            List<TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, RawMessage<String>>> creators){

        StrategyInitFactory.Builder builder = StrategyInitFactory.builder();
        for (TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, RawMessage<String>> creator : creators) {
            StrategyInitDatumType type = creator.getType();
            if (type.isValid()){
                builder.create(creator);
            } else {
                throw new BeanCreationException(String.format("Invalid type: %s", type.asStr()));
            }
        }
        Result<ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>>, RawMessage<String>> result = builder.check().calculateValue().buildResult();
        if (!result.getSuccess()){
            throw new BeanCreationException(result.getStatus().getCode());
        }

        return result.getValue();
    }

    @SneakyThrows
    @Bean
    public ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> extractorFactory(
            List<TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, RawMessage<String>>> creators
    ){
        ExtractorFactory.Builder builder = ExtractorFactory.builder();
        for (TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, RawMessage<String>> creator : creators) {
            ExtractorDatumType type = creator.getType();
            if (type.isValid()){
                builder.create(creator);
            } else {
                throw new BeanCreationException(String.format("Invalid type: %s", type.asStr()));
            }
        }
        Result<ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>>, RawMessage<String>> result = builder.check().calculateValue().buildResult();
        if (!result.getSuccess()){
            throw new BeanCreationException(result.getStatus().getCode());
        }

        return result.getValue();
    }

    @SneakyThrows
    @Bean
    public ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessage<String>> matcherFactory(
            List<TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, RawMessage<String>>> creators
    ){
        MatcherFactory.Builder builder = MatcherFactory.builder();
        for (TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, RawMessage<String>> creator : creators) {
            MatcherDatumType type = creator.getType();
            if (type.isValid()){
                builder.create(creator);
            } else {
                throw new BeanCreationException(String.format("Invalid type: %s", type.asStr()));
            }
        }
        Result<ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessage<String>>, RawMessage<String>> result = builder.check().calculateValue().buildResult();
        if (!result.getSuccess()){
            throw new BeanCreationException(result.getStatus().getCode());
        }
        return result.getValue();
    }
}
