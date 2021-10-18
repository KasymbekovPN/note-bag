package ru.kpn.matcher;

import java.util.function.Function;
import java.util.regex.Pattern;

// TODO: 18.10.2021 del 
class RegexMatcherOld implements Function<String, Boolean> {

    private final Pattern pattern;

    public RegexMatcherOld(String template) {
        pattern = Pattern.compile(template);
    }

    @Override
    public Boolean apply(String value) {
        return pattern.matcher(value).matches();
    }
}
