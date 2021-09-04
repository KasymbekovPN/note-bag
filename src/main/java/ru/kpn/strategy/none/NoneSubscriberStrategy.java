package ru.kpn.strategy.none;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.i18n.I18n;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategy.Matcher;

import java.util.Optional;

@Component
public class NoneSubscriberStrategy extends BaseSubscriberStrategy {

    private final Integer priority;
    private final I18n i18n;

    @Autowired
    public NoneSubscriberStrategy(@Value("${telegram.tube.strategies.noneSubscriberStrategy.priority}") Integer priority,
                                  I18n i18n) {
        super(new NoneSubscriberStrategyMatcher());
        this.priority = priority;
        this.i18n = i18n;
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(Update value) {
        return Optional.of(new SendMessage(calculateChatId(value), calculateMessage(value)));
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    private String calculateMessage(Update value) {
        return i18n.get("noneSubscriberStrategy.unknownInput", value.getMessage().getText());
    }

    private static class NoneSubscriberStrategyMatcher implements Matcher{
        @Override
        public Boolean match(String value) {
            return true;
        }
    }
}
