package ru.kpn.strategy;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.Optional;
import java.util.function.Function;

public interface Strategy<T, R> {
    StrategyCalculatorSource<String> runAndGetAnswer(T value);
    default Integer getPriority(){return -1;}
    Optional<R> execute(T value);

    // TODO: 30.10.2021 del NPB-100
    default void setMatcher(Function<Update, Boolean> matcher){}
}
