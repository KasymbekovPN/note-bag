package ru.kpn.calculator.strategy;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class TelegramMethodStrategyResultCalculatorOLd implements StrategyResultCalculatorOLd<BotApiMethod<?>, String> {

    @Override
    public BotApiMethod<?> calculate(String id, Object content) {
        return new SendMessage(id, String.valueOf(content));
    }
}
