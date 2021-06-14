package ru.kpn.tube.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class TelegramTubeRunner implements TubeRunner {

    private final AtomicBoolean run;
    private Runnable stopProcess;
    private Runnable startProcess;

    public TelegramTubeRunner(@Value("${telegram.tube.runner-init-value}") boolean initValue) {
        this.run = new AtomicBoolean(initValue);
    }

    @Override
    public AtomicBoolean isRun() {
        return run;
    }

    @Override
    public void stop() {
        this.run.set(false);
        runProcess(stopProcess);
    }

    @Override
    public void start() {
        this.run.set(true);
        runProcess(startProcess);
    }

    @Override
    public void setStopProcess(Runnable stopProcess) {
        this.stopProcess = stopProcess;
    }

    @Override
    public void setStartProcess(Runnable startProcess) {
        this.startProcess = startProcess;
    }

    private void runProcess(Runnable process) {
        if (process != null){
            process.run();
        } else {
            log.warn("Process is null; run is {}", run.get());
        }
    }
}
