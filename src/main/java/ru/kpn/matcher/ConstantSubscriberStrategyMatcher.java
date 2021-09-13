package ru.kpn.matcher;

import lombok.EqualsAndHashCode;

import java.util.function.Function;

// TODO: 04.09.2021 ?? del eq & hash
@EqualsAndHashCode
class ConstantSubscriberStrategyMatcher implements Function<String, Boolean> {

    private final boolean result;

    public ConstantSubscriberStrategyMatcher(boolean result) {
        this.result = result;
    }

    @Override
    public Boolean apply(String value) {
        return result;
    }
}
