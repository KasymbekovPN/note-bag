package ru.kpn.statusSeed.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.statusSeed.builder.StatusSeedBuilder;
import ru.kpn.statusSeed.builder.StringStatusSeedBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StringStatusSeedBuilderServiceTest {

    @Autowired
    private StringStatusSeedBuilderService service;

    @Test
    void shouldCheckTakenBuilder() {
        StatusSeedBuilder<String> builder = service.takeBuilder();
        assertThat(builder.getClass()).isEqualTo(StringStatusSeedBuilder.class);
    }
}
