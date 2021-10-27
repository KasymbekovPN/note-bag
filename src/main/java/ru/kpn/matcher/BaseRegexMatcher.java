package ru.kpn.matcher;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

abstract public class BaseRegexMatcher implements Function<Update, Boolean> {

    @Override
    public Boolean apply(Update update) {
        return checkUpdate(update) && match(update);
    }

    protected abstract boolean match(Update update);

    private Boolean checkUpdate(Update update) {
        if (update.hasMessage()){
            Message message = update.getMessage();
            return message.getChat() != null && message.getChatId() != null &&
                    message.getFrom() != null && message.getText() != null;
        }

        return false;
    }
}
