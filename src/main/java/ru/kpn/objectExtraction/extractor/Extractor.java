package ru.kpn.objectExtraction.extractor;

// TODO: 13.11.2021 rename???
public interface Extractor<R> {
    R getOrCreate(String key);
}
