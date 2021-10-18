package ru.kpn.matcher;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

public class MultiRegexMatcher implements Function<Update, Boolean> {

    private final Set<Pattern> patterns = new HashSet<>();

    public MultiRegexMatcher(Set<String> templates) {
        for (String template : templates) {
            patterns.add(Pattern.compile(template));
        }
    }

    @Override
    public Boolean apply(Update update) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(update.getMessage().getText()).matches()){
                return true;
            }
        }
        return false;
    }
}
