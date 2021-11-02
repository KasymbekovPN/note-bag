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
public class HelpStrategy extends BaseSubscriberStrategy {

    @Value("${telegram.tube.strategies.help.priority}")
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    // TODO: 02.11.2021 del
//    @Autowired
//    @Qualifier("helpMatcherOld")
//    public void setMatcherOld(Function<Update, Boolean> matcher){
//        this.matcher = matcher;
//    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        StrategyCalculatorSource<String> source = createSource("strategy.message.help");
        source.add(calculateChatId(value));

        return source;
    }
}
