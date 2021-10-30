package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.function.Function;

@Component
public class HelpSubscriberStrategy extends BaseSubscriberStrategy {

    @Value("${telegram.tube.strategies.helpSubscriberStrategy.priority}")
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Autowired
    @Qualifier("helpMatcher")
    public void setMatcher(Function<Update, Boolean> matcher){
        this.matcher = matcher;
    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        StrategyCalculatorSource<String> source = createSource("strategy.message.help");
        source.add(calculateChatId(value));

        return source;
    }
}
