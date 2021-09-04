package ru.kpn.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.calculator.strategy.StrategyResultCalculator;
import ru.kpn.i18n.I18n;

import java.util.Optional;

abstract public class BaseSubscriberStrategy implements SubscriberStrategy<Update, BotApiMethod<?>> {

    protected Integer priority;
    protected I18n i18n;
    protected Matcher matcher;
    protected StrategyResultCalculator<BotApiMethod<?>, String> resultCalculator;

    @Autowired
    public void setI18n(I18n i18n) {
        this.i18n = i18n;
    }

    @Autowired
    public void setResultCalculator(StrategyResultCalculator<BotApiMethod<?>, String> resultCalculator) {
        this.resultCalculator = resultCalculator;
    }

    @Override
    public Optional<BotApiMethod<?>> execute(Update value) {
        Optional<Message> maybeMessage = checkAndGetMessage(value);
        if (maybeMessage.isPresent()){
            if (matchTemplate(maybeMessage.get().getText())){
                return executeImpl(value);
            }
        }

        return Optional.empty();
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    private Optional<Message> checkAndGetMessage(Update value) {
        if (value.hasMessage()){
            Message message = value.getMessage();
            if (message.getChat() != null && message.getChatId() != null &&
                    message.getFrom() != null && message.getText() != null){
                return Optional.of(message);
            }
        }
        return Optional.empty();
    }

    private boolean matchTemplate(String text) {
        return matcher != null && matcher.match(text);
    }

    protected String calculateChatId(Update value) {
        return value.getMessage().getChatId().toString();
    }

    protected abstract Optional<BotApiMethod<?>> executeImpl(Update value);
}
