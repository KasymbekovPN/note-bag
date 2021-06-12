package ru.kpn.controller;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@DisplayName("Context test of WebHookController")
@SpringBootTest
public class WebHookControllerContextTest {

    @Autowired
    private WebHookController controller;

    @Test
    void shouldCheckControllerBeanExistence() {
        log.info("controller: {}", controller);
        Assertions.assertThat(controller).isNotNull();
    }
}
