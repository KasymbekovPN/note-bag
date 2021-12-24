package ru.kpn.strategy.calculaters.nameCalculator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.seed.Seed;
import ru.kpn.seed.SeedBuilderService;

import java.util.function.Function;

@Component
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class NameCalculator implements Function<Object, ValuedResult<String>> {

    @Autowired
    private SeedBuilderService<String> seedBuilderService;

    private String strategyBeanSuffix;

    @Override
    public ValuedResult<String> apply(Object value) {
        String simpleName = value.getClass().getSimpleName();
        int simpleNameLen = simpleName.length();
        int suffixLen = strategyBeanSuffix.length();
        if (simpleNameLen > suffixLen){
            int borderIndex = simpleNameLen - suffixLen;
            String suffix = simpleName.substring(borderIndex, simpleNameLen);
            if (strategyBeanSuffix.equals(suffix)){
                char[] chars = simpleName.substring(0, borderIndex).toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);

                return new ValuedResult<>(String.valueOf(chars));
            }
        }
        Seed<String> seed = seedBuilderService.takeNew().code("calculation.name.fail").arg(simpleName).arg(strategyBeanSuffix).build();
        return new ValuedResult<>(false, seed);
    }
}
