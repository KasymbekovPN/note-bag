package ru.kpn.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.calculator.strategy.StrategyResultCalculatorOLd;
import ru.kpn.i18n.builder.MessageBuilderFactory;

import java.util.Optional;
import java.util.function.Function;

abstract public class BaseSubscriberStrategy implements SubscriberStrategy<Update, BotApiMethod<?>> {

    protected Integer priority;
    protected MessageBuilderFactory messageBuilderFactory;
    protected Function<String, Boolean> matcher;
    protected StrategyResultCalculatorOLd<BotApiMethod<?>, String> resultCalculator;

    @Autowired
    public void setMessageBuilderFactory (MessageBuilderFactory messageBuilderFactory) {
        this.messageBuilderFactory = messageBuilderFactory;
    }

    @Autowired
    public void setResultCalculator(StrategyResultCalculatorOLd<BotApiMethod<?>, String> resultCalculator) {
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
        return matcher != null && matcher.apply(text);
    }

    protected String calculateChatId(Update value) {
        return value.getMessage().getChatId().toString();
    }

    // TODO: 22.09.2021 must return not-Optional 
    protected abstract Optional<BotApiMethod<?>> executeImpl(Update value);
}
