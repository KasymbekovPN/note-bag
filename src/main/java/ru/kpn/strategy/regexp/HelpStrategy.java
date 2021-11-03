package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

@Component
public class HelpStrategy extends BaseSubscriberStrategy {

    @Value("${telegram.tube.strategies.help.priority}")
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        StrategyCalculatorSource<String> source = createSource("strategy.message.help");
        source.add(calculateChatId(value));

        return source;
    }
}
