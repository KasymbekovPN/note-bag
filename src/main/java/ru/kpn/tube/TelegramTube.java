package ru.kpn.tube;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.runner.Runner;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@Service
class TelegramTube implements Tube<Update> {

    private final Consumer<Update> consumer;
    
    private final Runner runner;
    private final BlockingQueue<Update> queue;
    private final ExecutorService botES;
    private final ExecutorService tubeES = Executors.newSingleThreadExecutor(
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("telegram-tube-thread");
                return thread;
            }
    );

    public TelegramTube(Consumer<Update> consumer,
                        Runner runner,
                        @Value("${telegram.tube.default-queue-size}") int defaultQueueSize,
                        @Value("${telegram.tube.subscriber-thread-limit}") int consumerThreadLimit) {
        this.queue = new ArrayBlockingQueue<>(defaultQueueSize);
        this.botES = Executors.newFixedThreadPool(
                consumerThreadLimit,
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
        this.consumer = consumer;
        this.runner = runner;
        this.runner.setProcessesAndExecuteCurrent(this::startProcess, this::stopProcess);
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
                botES.submit(() -> consumer.accept(datum));
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
