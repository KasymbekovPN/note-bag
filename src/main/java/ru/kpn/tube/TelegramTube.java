package ru.kpn.tube;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
class TelegramTube implements Tube<Update> {

    // TODO: 12.06.2021 take from file
    private final static int DEFAULT_QUEUE_SIZE = 1_000;

    private final BlockingQueue<Update> queue = new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE);

    private TubeSubscriber<Update> rootSubscriber;

    @Override
    public synchronized boolean subscribe(TubeSubscriber<Update> subscriber) {
        rootSubscriber = subscriber.hookUp(rootSubscriber);
        return false;
    }

    @Override
    public synchronized void append(Update update) {

    }
}
