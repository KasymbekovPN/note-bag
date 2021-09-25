package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.runner.TubeRunner;
import ru.kpn.subscriber.Subscriber;
import ru.kpn.subscriptionManager.SubscriptionManager;
import utils.UpdateInstanceBuilder;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class TelegramTubeTest {

    private static final int DEFAULT_QUEUE_SIZE = 1_000;
    private static final int CONSUMER_THREAD_LIMIT = 2;

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckAppending.csv")
    void shouldCheckAppending(boolean runnerInitValue, boolean expectedResult) {
        TelegramTube tube = createTube(runnerInitValue, new TestSubscriptionManager());
        assertThat(tube.append(createUpdateInstance())).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckCheckConsumerAccepting.csv")
    void shouldCheckCheckConsumerAccepting(boolean runnerInitValue, boolean expectedResult) throws ExecutionException, InterruptedException {
        TestSubscriptionManager consumer = new TestSubscriptionManager();
        TelegramTube tube = createTube(runnerInitValue, consumer);
        tube.append(createUpdateInstance());
        Thread.sleep(150);

        assertThat(consumer.isAccepted()).isEqualTo(expectedResult);
    }

    private Update createUpdateInstance() {
        return new UpdateInstanceBuilder().chatId(123L).build();
    }

    private TelegramTube createTube(boolean runnerInitValue, SubscriptionManager<Update, BotApiMethod<?>> manager) {
        return new TelegramTube(manager, new TubeRunner(runnerInitValue), DEFAULT_QUEUE_SIZE, CONSUMER_THREAD_LIMIT);
    }

    private static class TestSubscriptionManager implements SubscriptionManager<Update, BotApiMethod<?>> {

        private boolean accepted = false;

        public boolean isAccepted() {
            return accepted;
        }

        @Override
        public void subscribe(Subscriber<Update, BotApiMethod<?>> subscriber) {
        }

        @Override
        public void execute(Update update) {
            accepted = true;
        }
    }
}