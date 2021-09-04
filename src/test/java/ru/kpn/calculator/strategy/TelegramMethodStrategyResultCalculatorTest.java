package ru.kpn.calculator.strategy;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.assertj.core.api.Assertions.assertThat;

public class TelegramMethodStrategyResultCalculatorTest {

    private static final String ID = "123";
    private static final String CONTENT = "some text";

    private final TelegramMethodStrategyResultCalculator calculator = new TelegramMethodStrategyResultCalculator();

    @Test
    void shouldCheckTypeOfInstance() {
        BotApiMethod<?> method = calculator.calculate(ID, CONTENT);
        assertThat(method.getClass()).isEqualTo(SendMessage.class);
    }

    @Test
    void shouldCheckId() {
        SendMessage method = (SendMessage) calculator.calculate(ID, CONTENT);
        assertThat(ID).isEqualTo(method.getChatId());
    }

    @Test
    void shouldCheckContent() {
        SendMessage method = (SendMessage) calculator.calculate(ID, CONTENT);
        assertThat(CONTENT).isEqualTo(method.getText());
    }
}
