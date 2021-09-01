package ru.kpn.tube.matcher;

import ru.kpn.tube.strategy.Matcher;

public interface SubscriberStrategyMatcherFactory {
    Matcher create(MatcherType matcherType, Object... args);
}
