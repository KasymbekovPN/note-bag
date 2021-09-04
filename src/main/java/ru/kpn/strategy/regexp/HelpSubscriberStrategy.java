package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategy.Matcher;

import java.util.Optional;

@Component
public class HelpSubscriberStrategy extends BaseSubscriberStrategy {

    @Value("${telegram.tube.strategies.helpSubscriberStrategy.text}")
    private String text;

    @Value("${telegram.tube.strategies.helpSubscriberStrategy.priority}")
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Autowired
    @Qualifier("helpStrategyMatcher")
    public void setMatcher(Matcher matcher){
        this.matcher = matcher;
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(Update value) {
        return Optional.of(resultCalculator.calculate(calculateChatId(value), text));
    }
}
