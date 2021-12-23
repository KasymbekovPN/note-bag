package ru.kpn.statusSeed;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 23.12.2021 del
@SpringBootTest
class BotRawMessageOldFactoryTest {

    private static final String CODE = "some.code";

    @Autowired
    private BotRawMessageFactoryOld factory;

    @Test
    void shouldCheckTypeOfCreatedInstance() {
        RawMessageOld<String> rawMessageOld = factory.create(CODE);
        assertThat(BotRawMessageOld.class).isEqualTo(rawMessageOld.getClass());
    }

    @Test
    void shouldCheckRawMessageContent() {
        RawMessageOld<String> rawMessageOld = factory.create(CODE);
        assertThat(CODE).isEqualTo(rawMessageOld.getCode());
    }
}