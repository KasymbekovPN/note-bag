package ru.kpn.creator;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.Map;

// TODO: 08.11.2021 rename
@Service
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class StrategyInitCreator {

    private Map<String, Datum> strategyInitData;

    public Result getDatum(String name){
        Result.ResultBuilder builder = Result.builder();
        if (strategyInitData.containsKey(name)){
            builder
                    .success(true)
                    .priority(strategyInitData.get(name).getPriority());
        } else {
            builder
                    .success(false)
                    .rawMessage(new BotRawMessage("priority.notSet.for").add(name));
        }

        return builder.build();
    }

    @Setter
    @Getter
    public static class Datum {
        private int priority;
    }

    @Getter
    @Builder
    public static class Result {
        private Boolean success;
        private int priority;
        private RawMessage<String> rawMessage;
    }
}
