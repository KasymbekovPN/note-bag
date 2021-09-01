package ru.kpn.matcher;

import ru.kpn.strategy.Matcher;

import java.util.Objects;
import java.util.regex.Pattern;

class RegexSubscriberStrategyMatcher implements Matcher {

    private final Pattern pattern;

    public RegexSubscriberStrategyMatcher(String template) {
        pattern = Pattern.compile(template);
    }

    @Override
    public Boolean match(String value) {
        return pattern.matcher(value).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegexSubscriberStrategyMatcher that = (RegexSubscriberStrategyMatcher) o;
        return Objects.equals(pattern.pattern(), that.pattern.pattern());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern);
    }
}
