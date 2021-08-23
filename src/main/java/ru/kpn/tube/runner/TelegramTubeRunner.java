package ru.kpn.tube.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kpn.bpp.logger.InjectLogger;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class TelegramTubeRunner implements TubeRunner {

    private final AtomicBoolean run;

    // TODO: 23.08.2021 restore
//    @InjectLogger
//    private Logger<CustomizableLogger.LogLevel> log;

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
        changeState(false, stopProcess);
    }

    @Override
    public void start() {
        changeState(true, startProcess);
    }

    @Override
    public void setStopProcess(Runnable stopProcess) {
        this.stopProcess = stopProcess;
    }

    @Override
    public void setStartProcess(Runnable startProcess) {
        this.startProcess = startProcess;
    }

    private void changeState(boolean newState, Runnable process){
        if (run.get() != newState){
            run.set(newState);
            runProcess(process);
        }
    }

    private void runProcess(Runnable process) {
        if (process != null){
            process.run();
        } else {
            log.warn("Process is null; run is {}", run.get());
        }
    }
}
