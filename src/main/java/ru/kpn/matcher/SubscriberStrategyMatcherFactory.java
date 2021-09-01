package ru.kpn.matcher;

import ru.kpn.strategy.Matcher;

public interface SubscriberStrategyMatcherFactory {
    Matcher create(MatcherType matcherType, Object... args);
}
