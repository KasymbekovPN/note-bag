package ru.kpn.tube.strategy.none;

import ru.kpn.tube.strategy.Matcher;

public class NoneSubscriberStrategyMatcher implements Matcher {
    @Override
    public boolean match(String value) {
        return true;
    }
}
