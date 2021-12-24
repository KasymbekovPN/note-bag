package ru.kpn.config.factory;

import lombok.SneakyThrows;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class ObjectFactoryConfig {

    @Autowired
    private StringSeedBuilderService seedBuilderService;

    @SneakyThrows
    @Bean
    public ObjectFactory<StrategyInitDatum, Integer, Seed<String>> strategyInitFactory(
            List<TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, Seed<String>>> creators){

        Map<StrategyInitDatumType, TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, Seed<String>>> creatorMap = new HashMap<>();
        for (TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, Seed<String>> creator : creators) {
            final StrategyInitDatumType type = creator.getType();
            if (type.isValid()){
                creatorMap.put(type, creator);
            } else {
                throw new BeanCreationException("notCompletely.creators.strategyInit");
            }
        }
        return new StrategyInitFactory(creatorMap, seedBuilderService);
    }

    @SneakyThrows
    @Bean
    public ObjectFactory<ExtractorDatum, Function<Update, String>, Seed<String>> extractorFactory(
            List<TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, Seed<String>>> creators
    ){
        Map<ExtractorDatumType, TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, Seed<String>>> creatorMap = new HashMap<>();
        for (TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, Seed<String>> creator : creators) {
            ExtractorDatumType type = creator.getType();
            if (type.isValid()){
                creatorMap.put(type, creator);
            } else {
                throw new BeanCreationException("notCompletely.creators.extractor");
            }
        }

        return new ExtractorFactory(creatorMap, seedBuilderService);
    }

    @SneakyThrows
    @Bean
    public ObjectFactory<MatcherDatum, Function<Update, Boolean>, Seed<String>> matcherFactory(
            List<TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>>> creators
    ){
        Map<MatcherDatumType, TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>>> creatorMap = new HashMap<>();
        for (TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>> creator : creators) {
            MatcherDatumType type = creator.getType();
            if (type.isValid()){
                creatorMap.put(type, creator);
            } else {
                throw new BeanCreationException("notCompletely.creators.matcher");
            }
        }

        return new MatcherFactory(creatorMap, seedBuilderService);
    }
}
