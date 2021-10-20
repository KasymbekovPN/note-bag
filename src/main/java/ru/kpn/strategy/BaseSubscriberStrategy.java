package ru.kpn.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.BotStrategyCalculatorSource;
import ru.kpn.strategyCalculator.StrategyCalculator;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.Optional;
import java.util.function.Function;

abstract public class BaseSubscriberStrategy implements Strategy<Update, BotApiMethod<?>> {

    private StrategyCalculator<BotApiMethod<?>, String> strategyCalculator;

    protected Function<Update, Boolean> matcher;
    protected Integer priority;

    @Autowired
    public void setStrategyCalculator(StrategyCalculator<BotApiMethod<?>, String> strategyCalculator) {
        this.strategyCalculator = strategyCalculator;
    }

    public abstract void setPriority(Integer priority);

    public abstract void setMatcher(Function<Update, Boolean> matcher);

    @Override
    public Integer getPriority() {
        return priority;
    }

    @Override
    public Optional<BotApiMethod<?>> execute(Update value) {
        return match(value) ? Optional.of(calculateBotApiMethod(value)) : Optional.empty();
    }

    private boolean match(Update value) {
        return matcher != null && matcher.apply(value);
    }

    private BotApiMethod<?> calculateBotApiMethod(Update value) {
        StrategyCalculatorSource<String> source = runAndGetAnswer(value);
        return strategyCalculator.calculate(source);
    }

    protected String calculateChatId(Update value) {
        return value.getMessage().getChatId().toString();
    }

    protected StrategyCalculatorSource<String> createSource(String code){
        return new BotStrategyCalculatorSource(code);
    }
}
