package ru.kpn.runner;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TubeRunnerContextTest {

    @Autowired
    private TubeRunner runner;

    @Test
    void shouldCheckRunnerBeanExistence() {
        log.info("runner: {}", runner);
        Assertions.assertThat(runner).isNotNull();
    }
}
