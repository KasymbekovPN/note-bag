package ru.kpn.strategy.calculaters.nameCalculator;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.statusSeed.BotRawMessageOld;
import ru.kpn.statusSeed.RawMessageOld;

import java.util.function.Function;

@Component
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class NameCalculator implements Function<Object, ValuedResult<String>> {

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
        RawMessageOld<String> status = new BotRawMessageOld("calculation.name.fail").add(simpleName).add(strategyBeanSuffix);
        return new ValuedResult<>(false, status);
    }
}
