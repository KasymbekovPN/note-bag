package ru.kpn.matcher;

import java.util.function.Function;

// TODO: 13.11.2021 del
public interface MatcherFactory<T, R> {
    Function<T, R> create(MatcherType matcherType, Object... args);
}
