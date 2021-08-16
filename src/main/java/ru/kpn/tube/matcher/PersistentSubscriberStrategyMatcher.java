package ru.kpn.tube.matcher;

import lombok.EqualsAndHashCode;
import ru.kpn.tube.strategy.Matcher;

@EqualsAndHashCode
class PersistentSubscriberStrategyMatcher implements Matcher {

    private final boolean result;

    public PersistentSubscriberStrategyMatcher(boolean result) {
        this.result = result;
    }

    @Override
    public Boolean match(String value) {
        return result;
    }
}
