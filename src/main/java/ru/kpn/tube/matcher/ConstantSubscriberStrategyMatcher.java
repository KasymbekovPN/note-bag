package ru.kpn.tube.matcher;

import lombok.EqualsAndHashCode;
import ru.kpn.tube.strategy.Matcher;

@EqualsAndHashCode
class ConstantSubscriberStrategyMatcher implements Matcher {

    private final boolean result;

    public ConstantSubscriberStrategyMatcher(boolean result) {
        this.result = result;
    }

    @Override
    public Boolean match(String value) {
        return result;
    }
}