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
public class GetStateSubscriberStrategy extends BaseSubscriberStrategy {

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    @Value("${telegram.tube.strategies.getStateSubscriberStrategy.priority}")
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Autowired
    @Qualifier("getStateStrategyMatcher")
    public void setMatcher(Function<String, Boolean> matcher){
        this.matcher = matcher;
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(Update value) {
        return Optional.of(resultCalculator.calculate(calculateChatId(value), getMessage(value.getMessage().getFrom())));
    }

    private String getMessage(User user) {
        return messageBuilderFactory
                .create("strategy.message.getstate")
                .arg(user.getId())
                .arg(stateService.get(user))
                .build();
    }
}
