package ru.kpn.strategy;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.RawMessage;

import java.util.Optional;
import java.util.function.Function;

public interface Strategy<T, R> {
    RawMessage<String> runAndGetAnswer(T value);
    Optional<R> execute(T value);
    default Integer getPriority(){return -1;}

    // TODO: 30.10.2021 del NPB-100
    default void setMatcherOld(Function<Update, Boolean> matcher){}
    default void setExtractorOld(Function<Update, String> extractor){}
}
