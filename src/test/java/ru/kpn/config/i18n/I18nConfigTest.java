package ru.kpn.config.i18n;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.i18n.I18n;

@Slf4j
@SpringBootTest
public class I18nConfigTest {

    @Autowired
    private I18n i18n;

    @Test
    void shouldDoSth() {
        // TODO: 02.09.2021 use other codes 
        String result = i18n.get("test", 1, 2);
        System.out.println(result);
    }
}
