package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.function.Function;

@Component
public class GetStateStrategy extends BaseSubscriberStrategy {

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    @Value("${telegram.tube.strategies.getState.priority}")
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Override
    public String getName() {
        return "getState";
    }

    // TODO: 02.11.2021 del
//    @Autowired
//    @Qualifier("getStateMatcherOld")
//    public void setMatcherOld(Function<Update, Boolean> matcher){
//        this.matcher = matcher;
//    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        StrategyCalculatorSource<String> source = createSource("strategy.message.getstate");
        source.add(calculateChatId(value));
        source.add(calculateChatId(value));
        source.add(stateService.get(getUser(value)));

        return source;
    }

    private User getUser(Update value) {
        return value.getMessage().getFrom();
    }
}