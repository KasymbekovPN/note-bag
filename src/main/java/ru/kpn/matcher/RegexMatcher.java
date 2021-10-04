package ru.kpn.matcher;

import java.util.function.Function;
import java.util.regex.Pattern;

class RegexMatcher implements Function<String, Boolean> {

    private final Pattern pattern;

    public RegexMatcher(String template) {
        pattern = Pattern.compile(template);
    }

    @Override
    public Boolean apply(String value) {
        return pattern.matcher(value).matches();
    }
}
