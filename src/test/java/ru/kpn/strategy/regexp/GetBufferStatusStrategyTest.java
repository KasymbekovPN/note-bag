package ru.kpn.strategy.regexp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetBufferStatusStrategyTest {

    private static final Long CHAT_ID = 123L;
    private static final String COMMAND = "/get buffer status";

    @Autowired
    private GetBufferStatusStrategy strategy;

    private UpdateInstanceBuilder builder;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(CHAT_ID);

        builder = new UpdateInstanceBuilder()
                .chatId(CHAT_ID)
                .from(user);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_getBufferStatus.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    // TODO: 29.09.2021 add test which checks answer of execute-method
}
