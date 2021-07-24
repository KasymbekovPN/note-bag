package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.logging.*;
import ru.kpn.service.logger.LoggerService;
import ru.kpn.service.logger.LoggerServiceImpl;
import ru.kpn.tube.runner.TelegramTubeRunner;
import ru.kpn.tube.runner.TubeRunner;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.HashSet;

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

    private LoggerService<CustomizableLogger.LogLevel> loggerService;

    @BeforeEach
    void setUp() {
        CustomizableLoggerSettings setting = CustomizableLoggerSettings.builder()
                .enable(CustomizableLogger.LogLevel.TRACE)
                .enable(CustomizableLogger.LogLevel.DEBUG)
                .enable(CustomizableLogger.LogLevel.INFO)
                .enable(CustomizableLogger.LogLevel.WARN)
                .enable(CustomizableLogger.LogLevel.DEBUG)
                .build();
        HashSet<Writer> writers = new HashSet<>() {{
            add(new SoutWriter());
        }};
        loggerService = new LoggerServiceImpl(setting, writers, new LoggerTemplateEngine(), new ArgsExtendingStrategy(), new TemplateExtendingStrategy());
    }

    @ParameterizedTest
    @MethodSource("getInitRunStateTestData")
    void shouldCheckInitialRunState(boolean initState) {
        TubeRunner runner = new TelegramTubeRunner(initState, loggerService);
        TelegramTube telegramTube = new TelegramTube(runner, 1_000, 2, loggerService);
        assertThat(telegramTube.getRunner()).isNotNull();
        assertThat(runner.isRun().get()).isEqualTo(telegramTube.getRunner().isRun().get());
    }

    @Test
    void shouldCheckSubscribeMethod() {
        TestTelegramTubeSubscriber subscriber = new TestTelegramTubeSubscriber();
        TelegramTube tube = new TelegramTube(new TelegramTubeRunner(true, loggerService), 1_000, 2, loggerService);
        tube.subscribe(subscriber);
        assertThat(subscriber.isPreviousIsNull()).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStartedTube() {
        TelegramTube tube = new TelegramTube(new TelegramTubeRunner(true, loggerService), 1_000, 2, loggerService);
        assertThat(tube.append(new Update())).isTrue();
    }

    @Test
    void shouldCheckAppendMethodOnStoppedTube() {
        TelegramTube tube = new TelegramTube(new TelegramTubeRunner(false, loggerService), 1_000, 2, loggerService);
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