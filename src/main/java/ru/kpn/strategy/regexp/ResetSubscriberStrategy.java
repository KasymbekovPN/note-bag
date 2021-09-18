package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.strategy.BaseSubscriberStrategy;

import java.util.Optional;
import java.util.function.Function;

@Component
public class ResetSubscriberStrategy extends BaseSubscriberStrategy {

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    @Value("${telegram.tube.strategies.resetSubscriberStrategy.priority}")
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Autowired
    @Qualifier("resetStrategyMatcher")
    public void setMatcher(Function<String, Boolean> matcher){
        this.matcher = matcher;
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(Update value) {
        User user = getUser(value);
        resetState(user);
        return Optional.of(resultCalculator.calculate(calculateChatId(value), getMessage(user)));
    }

    private User getUser(Update value) {
        return value.getMessage().getFrom();
    }

    private void resetState(User user) {
        stateService.set(user, NPBotState.RESET);
    }

    private String getMessage(User user) {
        return messageBuilderFactory
                .create("strategy.message.reset")
                .arg(user.getId())
                .build();
    }
}
