package ru.kpn.tube.runner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;
import ru.kpn.logging.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Testing of TelegramTubeRunner")
public class TelegramTubeRunnerTest {

    private int testStopProcessCounter;
    private int testStartProcessCounter;

    private static Object[][] getAfterInitTestData(){
        return new Object[][]{
                {false},
                {true}
        };
    }

    private static final CustomizableLogger logger = CustomizableLogger.builder(TelegramTubeRunner.class, CustomizableLoggerSettings.builder().build()).build();

    @ParameterizedTest
    @MethodSource("getAfterInitTestData")
    void shouldCheckRunStateAfterInit(boolean initValue) {
        TubeRunner runner = createRunner(initValue);
        assertThat(runner.isRun().get()).isEqualTo(initValue);
    }

    @Test
    void shouldCheckStopMethodAndStateChanging() {
        TubeRunner runner = createRunner(true);
        assertThat(runner.isRun().get()).isTrue();
        runner.stop();
        assertThat(runner.isRun().get()).isFalse();
    }

    @Test
    void shouldCheckStartMethodAndStateChanging() {
        TubeRunner runner = createRunner(false);
        assertThat(runner.isRun().get()).isEqualTo(false);
        runner.start();
        assertThat(runner.isRun().get()).isEqualTo(true);
    }

    @Test
    void shouldCheckStopProcess() {
        TubeRunner runner = createRunner(true);
        runner.stop();

        runner.start();
        runner.setStopProcess(this::testStopProcess);
        runner.stop();

        log.info("testStopProcessCounter: {}", testStopProcessCounter);
        assertThat(testStopProcessCounter).isEqualTo(1);
    }

    @Test
    void shouldCheckStartProcess() {
        TubeRunner runner = createRunner(false);
        runner.start();

        runner.stop();
        runner.setStartProcess(this::testStartProcess);
        runner.start();

        log.info("testStartProcessCounter: {}", testStartProcessCounter);
        assertThat(testStartProcessCounter).isEqualTo(1);
    }

    private TubeRunner createRunner(boolean initValue) {
        TubeRunner runner = new TelegramTubeRunner(initValue);
        ReflectionTestUtils.setField(runner, "log", logger);
        return runner;
    }

    private void testStopProcess(){
        testStopProcessCounter++;
    }

    private void testStartProcess(){
        testStartProcessCounter++;
    }
}
