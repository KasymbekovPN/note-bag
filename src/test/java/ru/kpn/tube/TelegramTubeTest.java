package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.logging.*;
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

    private static final CustomizableLogger logger = CustomizableLogger.builder(TelegramTube.class, CustomizableLoggerSettings.builder().build()).build();

    @ParameterizedTest
    @MethodSource("getInitRunStateTestData")
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