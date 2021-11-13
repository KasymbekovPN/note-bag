package ru.kpn.rawMessage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BotRawMessageFactoryTest {

    private static final String CODE = "some.code";

    @Autowired
    private BotRawMessageFactory factory;

    @Test
    void shouldCheckTypeOfCreatedInstance() {
        RawMessage<String> rawMessage = factory.create(CODE);
        assertThat(BotRawMessage.class).isEqualTo(rawMessage.getClass());
    }

    @Test
    void shouldCheckRawMessageContent() {
        RawMessage<String> rawMessage = factory.create(CODE);
        assertThat(CODE).isEqualTo(rawMessage.getCode());
    }
}