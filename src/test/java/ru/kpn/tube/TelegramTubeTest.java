package ru.kpn.tube;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kpn.logging.*;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.runner.TelegramTubeRunner;
import ru.kpn.tube.runner.TubeRunner;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TelegramTubeTest {
    private static final CustomizableLogger logger = CustomizableLogger.builder(TelegramTube.class, CustomizableLoggerSettings.builder().build()).build();

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckInitialRunState.csv")
    void shouldCheckInitialRunState(boolean initState) {
        TubeRunner runner = new TelegramTubeRunner(initState);
        TelegramTube tube = createTelegramTube(runner);
        assertThat(tube.getRunner()).isNotNull();
        assertThat(runner.isRun().get()).isEqualTo(tube.getRunner().isRun().get());
    }

    @Test
    void shouldCheckSubscribeMethod() {
        TestTelegramTubeSubscriber subscriber = new TestTelegramTubeSubscriber();
        TelegramTube tube = createTelegramTube(new TelegramTubeRunner(true));
        tube.subscribe(subscriber);
        assertThat(subscriber.isPreviousIsNull()).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStartedTube() {
        TelegramTube tube = createTelegramTube(new TelegramTubeRunner(true));
        assertThat(tube.append(TubeMessage.builder().build())).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStoppedTube() {
        TelegramTube tube = createTelegramTube(new TelegramTubeRunner(false));
        assertThat(tube.append(TubeMessage.builder().build())).isFalse();
    }

    private TelegramTube createTelegramTube(TubeRunner runner) {
        TelegramTube tube = new TelegramTube(runner, 1_000, 2);
        ReflectionTestUtils.setField(tube, "log", logger);
        return tube;
    }

    private static class TestTelegramTubeSubscriber implements TubeSubscriber<TubeMessage, BotApiMethod<?>>{

        private boolean previousIsNull;

        @Override
        public TubeSubscriber<TubeMessage, BotApiMethod<?>> hookUp(TubeSubscriber<TubeMessage, BotApiMethod<?>> previous) {
            previousIsNull = previous == null;
            return this;
        }

        @Override
        public Optional<BotApiMethod<?>> calculate(TubeMessage value) {
            return Optional.empty();
        }

        public boolean isPreviousIsNull() {
            return previousIsNull;
        }
    }
}