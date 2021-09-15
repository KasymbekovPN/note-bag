package ru.kpn.strategy.regexp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import utils.UpdateInstanceBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelpSubscriberStrategyTest {

    private static final Long CHAT_ID = 123L;

    @Autowired
    private HelpSubscriberStrategy strategy;

    @Autowired
    private MessageBuilderFactory messageBuilderFactory;

    @Value("${telegram.tube.strategies.helpSubscriberStrategy.priority}")
    private Integer expectedPriority;

    @ParameterizedTest
    @CsvFileSource(resources = "helpSubscriberStrategyTest.csv")
    void shouldCheckText(String command, boolean isPresent) {
        Update update = new UpdateInstanceBuilder()
                .chatId(CHAT_ID)
                .text(command)
                .build();
        Optional<BotApiMethod<?>> maybeMethod = strategy.execute(update);
        boolean maybeMethodPresent = maybeMethod.isPresent();
        assertThat(maybeMethodPresent).isEqualTo(isPresent);
        if (maybeMethodPresent){
            String expectedText = messageBuilderFactory.create("strategy.message.help").build();
            SendMessage sm = (SendMessage) maybeMethod.get();
            assertThat(sm.getChatId()).isEqualTo(CHAT_ID.toString());
            assertThat(sm.getText()).isEqualTo(expectedText);
        }
    }

    @Test
    void shouldCheckGetPriority() {
        assertThat(expectedPriority).isEqualTo(strategy.getPriority());
    }
}
