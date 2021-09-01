package ru.kpn.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class TubeRunner implements Runner {

    private final AtomicBoolean run;

    private Runnable stopProcess;
    private Runnable startProcess;

    public TubeRunner(@Value("${telegram.tube.runner-init-value}") boolean initValue) {
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

    @Override
    public void executeCurrentProcess() {
        runProcess(run.get()? startProcess : stopProcess);
    }

    @Override
    public void setProcessesAndExecuteCurrent(Runnable startProcess, Runnable stopProcess) {
        setStartProcess(startProcess);
        setStopProcess(stopProcess);
        executeCurrentProcess();
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
