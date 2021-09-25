package ru.kpn.subscriptionManager;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.StrategyCalculator;
import ru.kpn.subscriber.Subscriber;
import utils.UpdateInstanceBuilder;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BotSubscriptionManagerTest {

    private static final int PRIORITY = 1;
    private static final Long CHAT_ID = 1L;
    private static final String MESSAGE = "message";

    private BotSubscriptionManager subscriptionManager;

    @BeforeEach
    void setUp() {
        subscriptionManager = new BotSubscriptionManager(new TestStrategyCalculator());
        subscriptionManager.subscribe(new TestSubscriber(PRIORITY, CHAT_ID, MESSAGE));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckExecution.csv")
    void shouldCheckExecution(Long chatId, String expectedMessage, String expectedChatId) {
        Update update = new UpdateInstanceBuilder().chatId(chatId).build();
        SendMessage result = (SendMessage) subscriptionManager.execute(update);
        assertThat(expectedChatId).isEqualTo(result.getChatId());
        assertThat(expectedMessage).isEqualTo(result.getText());
    }

    @AllArgsConstructor
    private static class TestSubscriber implements Subscriber<Update, BotApiMethod<?>> {
        private final Integer priority;
        private final Long checkChatId;
        private final String message;

        @Override
        public Optional<BotApiMethod<?>> executeStrategy(Update value) {
            Long chatId = value.getMessage().getChatId();
            if (chatId.equals(checkChatId)){
                return Optional.of(new SendMessage(chatId.toString(), message));
            }
            return Optional.empty();
        }

        @Override
        public int compareTo(Subscriber<Update, BotApiMethod<?>> subscriber) {
            if (Objects.equals(priority, subscriber.getPriority())){
                return 0;
            }
            return priority > subscriber.getPriority() ? 1 : -1;
        }
    }

    private static class TestStrategyCalculator implements StrategyCalculator<BotApiMethod<?>> {
        private static final String DEFAULT_CHAT_ID = "0";
        private static final String DEFAULT_MESSAGE = "default message";

        @Override
        public BotApiMethod<?> calculate(String code, Object... args) {
            return new SendMessage(DEFAULT_CHAT_ID, DEFAULT_MESSAGE);
        }
    }
}
