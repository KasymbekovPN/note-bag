package ru.kpn.tube.runner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    @BeforeEach
    void setUp() {
        testStartProcessCounter = 0;
        testStopProcessCounter = 0;
    }

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

    @Test
    void shouldCheckExecuteCurrentProcessForStopState() {
        Runner runner = createRunner(false);
        runner.setStopProcess(this::testStopProcess);
        runner.executeCurrentProcess();

        assertThat(testStopProcessCounter).isEqualTo(1);
    }

    @Test
    void shouldCheckExecuteCurrentProcessForStartState() {
        Runner runner = createRunner(true);
        runner.setStartProcess(this::testStartProcess);
        runner.executeCurrentProcess();

        assertThat(testStartProcessCounter).isEqualTo(1);
    }

    @Test
    void shouldCheckSetProcessesAndExecuteCurrentForStopState() {
        Runner runner = createRunner(false);
        runner.setProcessesAndExecuteCurrent(this::testStartProcess, this::testStopProcess);
        assertThat(testStartProcessCounter).isEqualTo(0);
        assertThat(testStopProcessCounter).isEqualTo(1);
    }

    @Test
    void shouldCheckSetProcessesAndExecuteCurrentForStartState() {
        Runner runner = createRunner(true);
        runner.setProcessesAndExecuteCurrent(this::testStartProcess, this::testStopProcess);
        assertThat(testStartProcessCounter).isEqualTo(1);
        assertThat(testStopProcessCounter).isEqualTo(0);
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
