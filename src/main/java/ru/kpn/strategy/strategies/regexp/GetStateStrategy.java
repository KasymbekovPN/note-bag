package ru.kpn.strategy.strategies.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.strategy.strategies.BaseSubscriberStrategy;
import ru.kpn.rawMessage.RawMessageOld;

import java.util.function.Function;

@Component
public class GetStateStrategy extends BaseSubscriberStrategy {

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    @Inject(InjectionType.PRIORITY)
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Inject(InjectionType.MATCHER)
    public void setMatcher(Function<Update, Boolean> matcher){
        this.matcher = matcher;
    }

    @Override
    public RawMessageOld<String> runAndGetRawMessage(Update value) {
        return createRawMessage("strategy.message.getstate")
                .add(calculateChatId(value))
                .add(calculateChatId(value))
                .add(stateService.get(getUser(value)));
    }

    private User getUser(Update value) {
        return value.getMessage().getFrom();
    }
}
