package ru.kpn.strategy.injectors;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.rawMessage.RawMessage;

import java.util.Map;
import java.util.function.Function;

@Component
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class ExtractorInjector extends BaseInjector<ExtractorDatum, Function<Update, String>> {

    @Autowired
    private ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> factory;
    private Map<String, ExtractorDatum> extractorInitData;

    @Override
    protected ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> getFactory() {
        return factory;
    }

    @Override
    protected Map<String, ExtractorDatum> getInitData() {
        return extractorInitData;
    }

    @Override
    protected InnerInjector<ExtractorDatum, Function<Update, String>> createInnerInjector(Object object) {
        return new InnerInjector<>(object, InjectionType.EXTRACTOR);
    }
}
