package ru.kpn.config.decryptor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.decryptor.Decryptor;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class JasyptDecryptorConfigTest {

    @Autowired
    private Decryptor decryptor;

    @Test
    void shouldCheckDecryptorBean() {
        log.info("decryptor: {}", decryptor);
        assertThat(decryptor).isNotNull();
    }
}
