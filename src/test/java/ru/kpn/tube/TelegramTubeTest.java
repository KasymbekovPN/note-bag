package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.subscriber.TubeSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Test of TelegramTube")
class TelegramTubeTest {

    private Tube<Update> tube;

    @BeforeEach
    void setUp() {
        tube = new TelegramTube(1_000, 2);
    }

    @Test
    void shouldCheckInitialRunState() {
        tube.subscribe(new TestTelegramTubeSubscriber());
        assertThat(tube.isRun().get()).isTrue();
    }

    @Test
    void shouldCheckRunStateAfterStopping() {
        shouldCheckInitialRunState();
        tube.stop();
        assertThat(tube.isRun().get()).isFalse();
    }

    @Test
    void shouldCheckRunStateAfterStarting() {
        shouldCheckRunStateAfterStopping();
        tube.start();
        assertThat(tube.isRun().get()).isTrue();
    }

    @Test
    void shouldCheckSubscribeMethod() {
        TestTelegramTubeSubscriber subscriber = new TestTelegramTubeSubscriber();
        tube.subscribe(subscriber);
        assertThat(subscriber.isPreviousIsNull()).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStartedTube() {
        tube.start();
        assertThat(tube.append(new Update())).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStoppedTube() {
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