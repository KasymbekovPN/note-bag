package ru.kpn.strategy.injectors;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.seed.Seed;

import java.util.Map;

@Component
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class PriorityInjector extends BaseInjector<StrategyInitDatum, Integer> {

    @Autowired
    private ObjectFactory<StrategyInitDatum, Integer, Seed<String>> factory;
    private Map<String, StrategyInitDatum> strategyInitData;

    @Override
    protected InnerInjector<StrategyInitDatum, Integer> createInnerInjector(Object object) {
        return new InnerInjector<StrategyInitDatum, Integer>(object, InjectionType.PRIORITY);
    }

    @Override
    protected ObjectFactory<StrategyInitDatum, Integer, Seed<String>> getFactory() {
        return factory;
    }

    @Override
    protected Map<String, StrategyInitDatum> getInitData() {
        return strategyInitData;
    }
}
