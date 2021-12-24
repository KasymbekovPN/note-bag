package ru.kpn.seed;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StringSeedBuilderServiceTest {

    @Autowired
    private final SeedBuilderService<String> service = new StringSeedBuilderService();

    @Test
    void shouldCheckBuilderCreation() {
        SeedBuilder<String> builder = service.takeNew();
        assertThat(builder.getClass()).isEqualTo(StringSeedBuilder.class);
    }
}
