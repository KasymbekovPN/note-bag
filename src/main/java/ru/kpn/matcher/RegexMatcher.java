package ru.kpn.matcher;

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
        return pattern.matcher(update.getMessage().getText()).matches();
    }
}
