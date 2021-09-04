package ru.kpn.calculator.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class TelegramMethodStrategyResultCalculatorContextTest {

    @Autowired
    private StrategyResultCalculator<BotApiMethod<?>, String> calculator;

    @Test
    void shouldCheckExistence() {
        log.info("{}", calculator);
        assertThat(calculator).isNotNull();
    }
}
