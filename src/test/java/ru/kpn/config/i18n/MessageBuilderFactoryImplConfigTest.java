package ru.kpn.config.i18n;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.i18n.builder.MessageBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MessageBuilderFactoryImplConfigTest {

    @Autowired
    private MessageBuilderFactory messageBuilderFactory;

    @Test
    void shouldCheckMessageBuilderFactory() {
        log.info("{}", messageBuilderFactory);
        assertThat(messageBuilderFactory).isNotNull();
    }
}