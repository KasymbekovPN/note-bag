package ru.kpn.tube.runner;

import java.util.concurrent.atomic.AtomicBoolean;

public interface TubeRunner {
    default AtomicBoolean isRun(){return new AtomicBoolean();}
    default void stop() {}
    default void start(){}
    default void setStopProcess(Runnable stopProcess){}
    default void setStartProcess(Runnable startProcess) {}
}
