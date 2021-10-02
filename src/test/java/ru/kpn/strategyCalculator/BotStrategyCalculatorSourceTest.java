package ru.kpn.strategyCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class BotStrategyCalculatorSourceTest {

    private static final Random RANDOM = new Random();
    private static final String CODE = "some code";

    private BotStrategyCalculatorSource source;

    @BeforeEach
    void setUp() {
        source = new BotStrategyCalculatorSource(CODE);
    }

    @Test
    void shouldCheckCodeGetting() {
        assertThat(CODE).isEqualTo(source.getCode());
    }

    @RepeatedTest(10)
    void shouldCheckArgsGetting() {
        int size = RANDOM.nextInt(20);
        Object[] expectedObject = new Object[size];

        for (int i = 0; i < size; i++) {
            source.add(i);
            expectedObject[i] = i;
        }

        assertThat(expectedObject).isEqualTo(source.getArgs());
    }
}
