package ru.kpn.tube.runner;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TelegramTubeRunnerContextTest {

    @Autowired
    private TelegramTubeRunner runner;

    @Test
    void shouldCheckRunnerBeanExistence() {
        log.info("runner: {}", runner);
        Assertions.assertThat(runner).isNotNull();
    }
}
