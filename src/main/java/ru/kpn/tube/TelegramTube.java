package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.runner.TubeRunner;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
class TelegramTube implements Tube<Update> {

    private final TubeRunner runner;
    private final BlockingQueue<Update> queue;
    private final ExecutorService subscriberES;
    private final ExecutorService tubeES = Executors.newSingleThreadExecutor(
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("telegram-tube-thread");
                return thread;
            }
    );

    private TubeSubscriber<Update> rootSubscriber;

    public TelegramTube(TubeRunner runner,
                        @Value("${telegram.tube.default-queue-size}") int defaultQueueSize,
                        @Value("${telegram.tube.subscriber-thread-limit}") int subscriberThreadLimit) {
        this.queue = new ArrayBlockingQueue<>(defaultQueueSize);
        this.subscriberES = Executors.newFixedThreadPool(
                subscriberThreadLimit,
                new ThreadFactory() {
                    private final AtomicInteger threadCounter = new AtomicInteger(0);
                    @Override
                    public Thread newThread(Runnable runnable) {
                        Thread thread = new Thread(runnable);
                        thread.setName("subscriber-thread-" + threadCounter.incrementAndGet());
                        return thread;
                    }
                }
        );
        this.runner = runner;
        this.runner.setStartProcess(this::startProcess);
        this.runner.setStopProcess(this::stopProcess);

    }

    @Override
    public TubeRunner getRunner() {
        return runner;
    }

    @Override
    public synchronized void subscribe(TubeSubscriber<Update> subscriber) {
        rootSubscriber = subscriber.hookUp(rootSubscriber);
    }

    @Override
    public synchronized boolean append(Update update) {
        if (runner.isRun().get()){
            return queue.offer(update);
        } else {
            log.warn("Tube is stopped... reject : {}", update);
            return false;
        }
    }

    private void doTubeRoutine() {
        while (runner.isRun().get()){
            try{
                Update datum = queue.take();
                subscriberES.submit(() -> rootSubscriber.calculate(datum));
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
    }
    
    private void startProcess(){
        tubeES.submit(this::doTubeRoutine);
    }
    
    private void stopProcess(){
        tubeES.shutdownNow();
    }
}
