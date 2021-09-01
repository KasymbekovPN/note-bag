package ru.kpn.tube.strategy;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

abstract public class BaseSubscriberStrategy implements SubscriberStrategy<Update, BotApiMethod<?>> {

    protected Matcher matcher;

    public BaseSubscriberStrategy(){}

    public BaseSubscriberStrategy(Matcher matcher) {
        this.matcher = matcher;
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
