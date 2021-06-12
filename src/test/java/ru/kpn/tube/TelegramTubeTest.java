package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("Test of TelegramTube")
@SpringBootTest
class TelegramTubeTest {

    @Autowired
    private TelegramTube tube;

    @Test
    void shouldDoSth() {
        log.info("{}", tube);
    }
}