package ru.kpn.config.factory;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.datum.StrategyInitDatum;
import ru.kpn.objectExtraction.factory.StrategyInitFactory;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.rawMessage.RawMessage;

import java.util.List;
import java.util.Map;

@Configuration
public class ObjectFactoryConfig {


    //<
//    @Bean
//    public ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> strategyInitFactory(List<Creator<StrategyInitDatum, Integer, RawMessage<String>>> creators){
//        StrategyInitFactory.Builder builder = StrategyInitFactory.builder();
//        for (Creator<StrategyInitDatum, Integer, RawMessage<String>> creator : creators) {
//
//        }
//    }

    // TODO: 04.12.2021 del
//    private Map<String, MatcherDatum> matcherInitData;
//    private Map<String, ExtractorDatum> extractorInitData;
//    private Map<String, StrategyInitDatum> strategyInitData;
//    @Bean
//    public String t(){
//
//        for (Map.Entry<String, MatcherDatum> entry : matcherInitData.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//
//        return "";
//    }
}
