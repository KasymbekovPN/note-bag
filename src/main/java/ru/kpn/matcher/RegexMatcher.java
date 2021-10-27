package ru.kpn.matcher;

import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.regex.Pattern;

public class RegexMatcher extends BaseRegexMatcher {

    private final Pattern pattern;

    public RegexMatcher(String template) {
        pattern = Pattern.compile(template);
    }

    @Override
    protected boolean match(Update update) {
        return pattern.matcher(update.getMessage().getText()).matches();
    }
}
