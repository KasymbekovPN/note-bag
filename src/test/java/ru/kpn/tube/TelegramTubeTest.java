package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.runner.TelegramTubeRunner;
import ru.kpn.tube.runner.TubeRunner;
import ru.kpn.tube.subscriber.TubeSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Test of TelegramTube")
class TelegramTubeTest {

    private static Object[][] getInitRunStateTestData(){
        return new Object[][]{
                {false},
                {true}
        };
    }

    @ParameterizedTest
    @MethodSource("getInitRunStateTestData")
    void shouldCheckInitialRunState(boolean initState) {
        TubeRunner runner = new TelegramTubeRunner(initState);
        TelegramTube telegramTube = new TelegramTube(runner, 1_000, 2);
        assertThat(telegramTube.getRunner()).isNotNull();
        assertThat(runner.isRun().get()).isEqualTo(telegramTube.getRunner().isRun().get());
    }

    @Test
    void shouldCheckSubscribeMethod() {
        TestTelegramTubeSubscriber subscriber = new TestTelegramTubeSubscriber();
        TelegramTube tube = new TelegramTube(new TelegramTubeRunner(true), 1_000, 2);
        tube.subscribe(subscriber);
        assertThat(subscriber.isPreviousIsNull()).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStartedTube() {
        TelegramTube tube = new TelegramTube(new TelegramTubeRunner(true), 1_000, 2);
        assertThat(tube.append(new Update())).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStoppedTube() {
        TelegramTube tube = new TelegramTube(new TelegramTubeRunner(false), 1_000, 2);
        assertThat(tube.append(new Update())).isFalse();
    }

    private static class TestTelegramTubeSubscriber implements TubeSubscriber<Update>{

        private boolean previousIsNull;

        @Override
        public TubeSubscriber<Update> hookUp(TubeSubscriber<Update> previous) {
            previousIsNull = previous == null;
            return this;
        }

        public boolean isPreviousIsNull() {
            return previousIsNull;
        }
    }
}