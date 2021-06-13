package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
class TelegramTube implements Tube<Update> {

    private final AtomicBoolean run = new AtomicBoolean(false);
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

    public TelegramTube(@Value("${telegram.tube.default-queue-size}") int defaultQueueSize,
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
    }

    @Override
    public AtomicBoolean isRun() {
        return run;
    }

    @Override
    public void stop() {
        run.set(false);
    }

    @Override
    public void start() {
        run.set(true);
        tubeES.submit(this::doTubeRoutine);
    }

    @Override
    public synchronized void subscribe(TubeSubscriber<Update> subscriber) {
        start();
        rootSubscriber = subscriber.hookUp(rootSubscriber);
    }

    @Override
    public synchronized boolean append(Update update) {
        if (run.get()){
            return queue.offer(update);
        } else {
            log.warn("Tube is stopped... reject : {}", update);
            return false;
        }
    }

    private void doTubeRoutine() {
        while (run.get()){
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
}
