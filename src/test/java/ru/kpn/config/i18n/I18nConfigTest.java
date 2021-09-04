package ru.kpn.config.i18n;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.i18n.I18n;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class I18nConfigTest {

    @Autowired
    private I18n i18n;

    @Test
    void shouldDoSth() {
        log.info("{}", i18n);
        assertThat(i18n).isNotNull();
    }
}
