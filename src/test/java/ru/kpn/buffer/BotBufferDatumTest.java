package ru.kpn.buffer;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class BotBufferDatumTest {

    private static final Random RANDOM = new Random();

    @Test
    void shouldCheckTypeGetting() {
        for (BufferDatumType type : BufferDatumType.values()) {
            BotBufferDatum datum = new BotBufferDatum(type, "");
            assertThat(type).isEqualTo(datum.getType());
        }
    }

    @RepeatedTest(10)
    public void shouldCheckContentGetting() {
        String expectedContent = String.valueOf(RANDOM.nextInt());
        BotBufferDatum datum = new BotBufferDatum(BufferDatumType.SIMPLE_TEXT, expectedContent);
        assertThat(expectedContent).isEqualTo(datum.getContent());
    }
}
