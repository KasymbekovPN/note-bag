package ru.kpn.tube;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.logging.*;
import ru.kpn.tube.runner.TelegramTubeRunner;
import ru.kpn.tube.runner.TubeRunner;
import ru.kpn.tube.subscriber.TubeSubscriber;

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
        tube.subscribe(subscriber);
        assertThat(subscriber.isPreviousIsNull()).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStartedTube() {
        TelegramTube tube = createTelegramTube(new TelegramTubeRunner(true));
        assertThat(tube.append(new Update())).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStoppedTube() {
        TelegramTube tube = createTelegramTube(new TelegramTubeRunner(false));
        assertThat(tube.append(new Update())).isFalse();
    }

    private TelegramTube createTelegramTube(TubeRunner runner) {
        TelegramTube tube = new TelegramTube(runner, 1_000, 2);
        ReflectionTestUtils.setField(tube, "log", logger);
        return tube;
    }

    private static class TestTelegramTubeSubscriber implements TubeSubscriber<Update, BotApiMethod<?>>{

        private boolean previousIsNull;

        @Override
        public TubeSubscriber<Update, BotApiMethod<?>> setNext(TubeSubscriber<Update, BotApiMethod<?>> next) {
            previousIsNull = true;
            return TubeSubscriber.super.setNext(next);
        }

        public boolean isPreviousIsNull() {
            return previousIsNull;
        }
    }
}