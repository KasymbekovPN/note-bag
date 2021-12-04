package ru.kpn.config.factory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ObjectFactoryConfigTest {

    @Autowired
    ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> strategyInitFactory;
    @Autowired
    ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> extractorFactory;
    @Autowired
    ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessage<String>> matcherFactory;

    @Test
    void shouldCheckStrategyInitFactoryCreation() {
        assertThat(strategyInitFactory).isNotNull();
    }

    @Test
    void shouldCheckExtractorFactoryCreation() {
        assertThat(extractorFactory).isNotNull();
    }

    @Test
    void shouldCheckMatcherFactoryCreation() {
        assertThat(matcherFactory).isNotNull();
    }
}