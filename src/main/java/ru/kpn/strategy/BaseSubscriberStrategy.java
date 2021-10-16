package ru.kpn.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.BotStrategyCalculatorSource;
import ru.kpn.strategyCalculator.StrategyCalculator;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.Optional;
import java.util.function.Function;

abstract public class BaseSubscriberStrategy implements Strategy<Update, BotApiMethod<?>> {

    private StrategyCalculator<BotApiMethod<?>, String> strategyCalculator;

    protected Function<String, Boolean> matcher;
    protected Integer priority;

    @Autowired
    public void setStrategyCalculator(StrategyCalculator<BotApiMethod<?>, String> strategyCalculator) {
        this.strategyCalculator = strategyCalculator;
    }

    public abstract void setPriority(Integer priority);

    public abstract void setMatcher(Function<String, Boolean> matcher);

    @Override
    public Integer getPriority() {
        return priority;
    }

    @Override
    public Optional<BotApiMethod<?>> execute(Update value) {
        Optional<Message> maybeMessage = checkAndGetMessage(value);
        if (maybeMessage.isPresent()){
            if (matchTemplate(maybeMessage.get().getText())){
                return Optional.of(calculateBotApiMethod(value));
            }
        }

        return Optional.empty();
    }

    private Optional<Message> checkAndGetMessage(Update value) {
        if (value.hasMessage()){
            Message message = value.getMessage();
            if (message.getChat() != null && message.getChatId() != null &&
                    message.getFrom() != null && message.getText() != null){
                return Optional.of(message);
            }
        }
        return Optional.empty();
    }

    private boolean matchTemplate(String text) {
        return matcher != null && matcher.apply(text);
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
