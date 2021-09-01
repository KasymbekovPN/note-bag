package ru.kpn.runner;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Runner {
    default AtomicBoolean isRun(){return new AtomicBoolean();}
    default void stop() {}
    default void start(){}
    default void setStopProcess(Runnable stopProcess){}
    default void setStartProcess(Runnable startProcess) {}
    default void executeCurrentProcess(){}
    default void setProcessesAndExecuteCurrent(Runnable startProcess, Runnable stopProcess){}
}
