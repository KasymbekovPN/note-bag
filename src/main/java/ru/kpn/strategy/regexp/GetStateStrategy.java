package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.RawMessage;

@Component
public class GetStateStrategy extends BaseSubscriberStrategy {

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    @Inject(InjectionType.PRIORITY)
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Override
    public RawMessage<String> runAndGetAnswer(Update value) {
        RawMessage<String> source = createSource("strategy.message.getstate");
        source.add(calculateChatId(value));
        source.add(calculateChatId(value));
        source.add(stateService.get(getUser(value)));

        return source;
    }

    private User getUser(Update value) {
        return value.getMessage().getFrom();
    }
}
