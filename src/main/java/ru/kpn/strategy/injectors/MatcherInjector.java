package ru.kpn.strategy.injectors;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.seed.Seed;

import java.util.Map;
import java.util.function.Function;

@Component
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class MatcherInjector extends BaseInjector<MatcherDatum, Function<Update, Boolean>> {

    @Autowired
    private ObjectFactory<MatcherDatum, Function<Update, Boolean>, Seed<String>> factory;
    private Map<String, MatcherDatum> matcherInitData;

    @Override
    protected ObjectFactory<MatcherDatum, Function<Update, Boolean>, Seed<String>> getFactory() {
        return factory;
    }

    @Override
    protected Map<String, MatcherDatum> getInitData() {
        return matcherInitData;
    }

    @Override
    protected InnerInjector<MatcherDatum, Function<Update, Boolean>> createInnerInjector(Object object) {
        return new InnerInjector<MatcherDatum, Function<Update, Boolean>>(object, InjectionType.MATCHER);
    }
}
