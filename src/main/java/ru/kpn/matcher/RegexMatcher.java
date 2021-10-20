package ru.kpn.matcher;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;
import java.util.regex.Pattern;

public class RegexMatcher implements Function<Update, Boolean> {

    private final Pattern pattern;

    public RegexMatcher(String template) {
        pattern = Pattern.compile(template);
    }

    @Override
    public Boolean apply(Update update) {
        return checkUpdate(update) && pattern.matcher(update.getMessage().getText()).matches();
    }

    // TODO: 20.10.2021 to super?
    private Boolean checkUpdate(Update update) {
        if (update.hasMessage()){
            Message message = update.getMessage();
            return message.getChat() != null && message.getChatId() != null &&
                    message.getFrom() != null && message.getText() != null;
        }

        return false;
    }
}
