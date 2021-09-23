package ru.kpn.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.calculator.strategy.StrategyResultCalculatorOLd;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.strategyCalculator.StrategyCalculator;

import java.util.Optional;
import java.util.function.Function;

abstract public class BaseSubscriberStrategy implements Strategy<Update, BotApiMethod<?>> {



    // TODO: 23.09.2021 del 
//    protected MessageBuilderFactory messageBuilderFactory;
    

    protected StrategyCalculator<BotApiMethod<?>> strategyCalculator;
    protected Integer priority;
    protected Function<String, Boolean> matcher;

    // TODO: 23.09.2021 del 
//    protected StrategyResultCalculatorOLd<BotApiMethod<?>, String> resultCalculator;

    // TODO: 23.09.2021 del 
//    @Autowired
//    public void setMessageBuilderFactory (MessageBuilderFactory messageBuilderFactory) {
//        this.messageBuilderFactory = messageBuilderFactory;
//    }

    // TODO: 23.09.2021 del
//    @Autowired
//    public void setResultCalculator(StrategyResultCalculatorOLd<BotApiMethod<?>, String> resultCalculator) {
//        this.resultCalculator = resultCalculator;
//    }

    @Autowired
    public void setStrategyCalculator(StrategyCalculator<BotApiMethod<?>> strategyCalculator) {
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
                return Optional.of(executeImpl(value));
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

    protected String calculateChatId(Update value) {
        return value.getMessage().getChatId().toString();
    }

    protected abstract BotApiMethod<?> executeImpl(Update value);
}
