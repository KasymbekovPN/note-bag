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
public class TubeRunnerTest {

    private int testStopProcessCounter;
    private int testStartProcessCounter;

    private static Object[][] getAfterInitTestData(){
        return new Object[][]{
                {false},
                {true}
        };
    }

    private static final CustomizableLogger logger = CustomizableLogger.builder(TubeRunner.class, CustomizableLoggerSettings.builder().build()).build();

    @ParameterizedTest
    @MethodSource("getAfterInitTestData")
    void shouldCheckRunStateAfterInit(boolean initValue) {
        Runner runner = createRunner(initValue);
        assertThat(runner.isRun().get()).isEqualTo(initValue);
    }

    @Test
    void shouldCheckStopMethodAndStateChanging() {
        Runner runner = createRunner(true);
        assertThat(runner.isRun().get()).isTrue();
        runner.stop();
        assertThat(runner.isRun().get()).isFalse();
    }

    @Test
    void shouldCheckStartMethodAndStateChanging() {
        Runner runner = createRunner(false);
        assertThat(runner.isRun().get()).isEqualTo(false);
        runner.start();
        assertThat(runner.isRun().get()).isEqualTo(true);
    }

    @Test
    void shouldCheckStopProcess() {
        Runner runner = createRunner(true);
        runner.stop();

        runner.start();
        runner.setStopProcess(this::testStopProcess);
        runner.stop();

        log.info("testStopProcessCounter: {}", testStopProcessCounter);
        assertThat(testStopProcessCounter).isEqualTo(1);
    }

    @Test
    void shouldCheckStartProcess() {
        Runner runner = createRunner(false);
        runner.start();

        runner.stop();
        runner.setStartProcess(this::testStartProcess);
        runner.start();

        log.info("testStartProcessCounter: {}", testStartProcessCounter);
        assertThat(testStartProcessCounter).isEqualTo(1);
    }

    private Runner createRunner(boolean initValue) {
        return new TubeRunner(initValue);
    }

    private void testStopProcess(){
        testStopProcessCounter++;
    }

    private void testStartProcess(){
        testStartProcessCounter++;
    }
}
