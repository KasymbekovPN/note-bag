package ru.kpn.buffer;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class BotBuffer implements Buffer<Long, BufferDatum<BufferDatumType, String>> {

    private final Map<Long, Queue<BufferDatum<BufferDatumType, String>>> queues = new ConcurrentHashMap<>();

    @Override
    public int add(Long id, BufferDatum<BufferDatumType, String> datum) {
        if (!queues.containsKey(id)){
            queues.put(id, new LinkedBlockingQueue<>());
        }
        queues.get(id).add(datum);
        return queues.get(id).size();
    }

    @Override
    public int getSize(Long key) {
        return queues.containsKey(key) ? queues.get(key).size() : 0;
    }

    @Override
    public Optional<BufferDatum<BufferDatumType, String>> peek(Long key) {
        if (queues.containsKey(key)){
            BufferDatum<BufferDatumType, String> datum = queues.get(key).peek();
            return datum == null ? Optional.empty() : Optional.of(datum);
        }
        return Optional.empty();
    }

    @Override
    public Optional<BufferDatum<BufferDatumType, String>> poll(Long key) {
        if (queues.containsKey(key)){
            BufferDatum<BufferDatumType, String> datum = queues.get(key).poll();
            return datum == null ? Optional.empty() : Optional.of(datum);
        }
        return Optional.empty();
    }

    @Override
    public void clear(Long key) {
        if (queues.containsKey(key)){
            queues.get(key).clear();
        }
    }

    @Override
    public BufferDatum<BufferDatumType, String> createDatum(Object... args) {
        return new BotBufferDatum((BufferDatumType) args[0], String.valueOf(args[1]));
    }
}
