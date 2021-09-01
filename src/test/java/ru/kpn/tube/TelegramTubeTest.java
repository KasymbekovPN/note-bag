package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.runner.TubeRunner;
import utils.UpdateInstanceBuilder;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class TelegramTubeTest {

    private static final int DEFAULT_QUEUE_SIZE = 1_000;
    private static final int CONSUMER_THREAD_LIMIT = 2;

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckAppending.csv")
    void shouldCheckAppending(boolean runnerInitValue, boolean expectedResult) {
        TelegramTube tube = createTube(runnerInitValue, new TestConsumer());
        assertThat(tube.append(createUpdateInstance())).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckCheckConsumerAccepting.csv")
    void shouldCheckCheckConsumerAccepting(boolean runnerInitValue, boolean expectedResult) throws ExecutionException, InterruptedException {
        TestConsumer consumer = new TestConsumer();
        TelegramTube tube = createTube(runnerInitValue, consumer);
        tube.append(createUpdateInstance());
        Thread.sleep(150);

        assertThat(consumer.isAccepted()).isEqualTo(expectedResult);
    }

    private Update createUpdateInstance() {
        return new UpdateInstanceBuilder().chatId(123L).build();
    }

    private TelegramTube createTube(boolean runnerInitValue, Consumer<Update> consumer) {
        return new TelegramTube(consumer, new TubeRunner(runnerInitValue), DEFAULT_QUEUE_SIZE, CONSUMER_THREAD_LIMIT);
    }

    private static class TestConsumer implements Consumer<Update> {

        private boolean accepted = false;

        @Override
        public void accept(Update update) {
            accepted = true;
            log.info("in accept : {}", update);
        }

        public boolean isAccepted() {
            return accepted;
        }
    }
}