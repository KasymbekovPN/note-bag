package ru.kpn.matcher;

import java.util.function.Function;

// TODO: 13.11.2021 del
public interface MatcherFactoryOld<T, R> {
    Function<T, R> create(MatcherType matcherType, Object... args);
}
