package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class TelegramTubeContextTest {

    @Autowired
    private TelegramTube tube;

    @Test
    void shouldCheckTelegramTubeBeanExistence() {
        log.info("telegramTube: {}", tube);
        assertThat(tube).isNotNull();
    }
}
