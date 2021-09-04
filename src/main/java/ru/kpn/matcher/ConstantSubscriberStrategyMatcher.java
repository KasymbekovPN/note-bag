package ru.kpn.matcher;

import lombok.EqualsAndHashCode;
import ru.kpn.strategy.Matcher;

// TODO: 04.09.2021 ?? del eq & hash
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
