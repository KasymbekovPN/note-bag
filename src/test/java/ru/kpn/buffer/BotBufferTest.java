package ru.kpn.buffer;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BotBufferTest {

    private static final int CONTENT_BEGIN = 0;
    private static final int CONTENT_END = 1_000;
    private static final Long USER_ID = 123L;

    private BotBuffer buffer;

    @BeforeEach
    void setUp() {
        buffer = new BotBuffer();
    }

    @Test
    void checkDatumAddition() {
        for (int i = CONTENT_BEGIN; i < CONTENT_END; i++) {
            int size = buffer.add(USER_ID, new TestDatum(BufferDatumType.LINK, ""));
            assertThat(size).isEqualTo(i+1);
        }
    }

    @Test
    void shouldCheckQueueSize() {
        assertThat(0).isEqualTo(buffer.getSize(USER_ID));
        fillBuffer(buffer);
        assertThat(CONTENT_END).isEqualTo(buffer.getSize(USER_ID));
    }

    @Test
    void shouldCheckPeekingOnEmptyQueue() {
        assertThat(buffer.peek(USER_ID)).isEmpty();
    }

    @Test
    void shouldCheckPeeking() {
        int expectedSize = fillBuffer(buffer);

        Optional<BufferDatum<BufferDatumType, String>> maybeDatum = buffer.peek(USER_ID);
        assertThat(maybeDatum).isPresent();

        BufferDatum<BufferDatumType, String> datum = maybeDatum.get();
        assertThat(expectedSize).isEqualTo(buffer.getSize(USER_ID));
        assertThat(String.valueOf(CONTENT_BEGIN)).isEqualTo(datum.getContent());
    }

    @Test
    void shouldCheckPolling() {
        int expectedSize = fillBuffer(buffer) - 1;

        Optional<BufferDatum<BufferDatumType, String>> maybeDatum = buffer.poll(USER_ID);
        assertThat(maybeDatum).isPresent();

        BufferDatum<BufferDatumType, String> datum = maybeDatum.get();
        assertThat(expectedSize).isEqualTo(buffer.getSize(USER_ID));
        assertThat(String.valueOf(CONTENT_BEGIN)).isEqualTo(datum.getContent());
    }

    @Test
    void shouldCheckClearing() {
        fillBuffer(buffer);
        buffer.clear(USER_ID);
        assertThat(buffer.getSize(USER_ID)).isZero();
    }

    private int fillBuffer(BotBuffer buffer){
        int size = 0;
        for (int i = CONTENT_BEGIN; i < CONTENT_END; i++) {
            size = buffer.add(USER_ID, new TestDatum(BufferDatumType.LINK, String.valueOf(i)));
        }
        return size;
    }

    @AllArgsConstructor
    private static class TestDatum implements BufferDatum<BufferDatumType, String> {
        private final BufferDatumType type;
        private String content;

        @Override
        public BufferDatumType getType() {
            return type;
        }

        @Override
        public String getContent() {
            return content;
        }
    }
}
