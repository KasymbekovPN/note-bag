package ru.kpn.tube.matcher;

import ru.kpn.tube.strategy.Matcher;

import java.util.regex.Pattern;

public class RegExpSubscriberStrategyMatcher implements Matcher {

    private final Pattern pattern;

    public RegExpSubscriberStrategyMatcher(String template) {
        pattern = Pattern.compile(template);
    }

    @Override
    public Boolean match(String value) {
        return pattern.matcher(value).matches();
    }
}
