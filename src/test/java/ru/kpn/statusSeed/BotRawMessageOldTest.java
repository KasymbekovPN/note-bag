package ru.kpn.statusSeed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 23.12.2021 del
public class BotRawMessageOldTest {

    private static final Random RANDOM = new Random();
    private static final String CODE = "some code";

    private BotRawMessageOld source;

    @BeforeEach
    void setUp() {
        source = new BotRawMessageOld(CODE);
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
