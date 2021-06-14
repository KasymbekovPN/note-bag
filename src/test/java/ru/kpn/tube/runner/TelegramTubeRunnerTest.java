package ru.kpn.tube.runner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest
    @MethodSource("getAfterInitTestData")
    void shouldCheckRunStateAfterInit(boolean initValue) {
        TubeRunner runner = new TelegramTubeRunner(initValue);
        assertThat(runner.isRun().get()).isEqualTo(initValue);
    }

    @Test
    void shouldCheckStopMethodAndStateChanging() {
        TubeRunner runner = new TelegramTubeRunner(true);
        assertThat(runner.isRun().get()).isTrue();
        runner.stop();
        assertThat(runner.isRun().get()).isFalse();
    }

    @Test
    void shouldCheckStartMethodAndStateChanging() {
        TubeRunner runner = new TelegramTubeRunner(false);
        assertThat(runner.isRun().get()).isEqualTo(false);
        runner.start();
        assertThat(runner.isRun().get()).isEqualTo(true);
    }

    @Test
    void shouldCheckStopProcess() {
        TubeRunner runner = new TelegramTubeRunner(true);
        runner.stop();

        runner.start();
        runner.setStopProcess(this::testStopProcess);
        runner.stop();

        log.info("testStopProcessCounter: {}", testStopProcessCounter);
        assertThat(testStopProcessCounter).isEqualTo(1);
    }

    @Test
    void shouldCheckStartProcess() {
        TubeRunner runner = new TelegramTubeRunner(false);
        runner.start();

        runner.stop();
        runner.setStartProcess(this::testStartProcess);
        runner.start();

        log.info("testStartProcessCounter: {}", testStartProcessCounter);
        assertThat(testStartProcessCounter).isEqualTo(1);
    }

    private void testStopProcess(){
        testStopProcessCounter++;
    }

    private void testStartProcess(){
        testStartProcessCounter++;
    }
}
