package ru.kpn.extractor;

import java.util.function.Function;

// TODO: 13.11.2021 del
public interface ExtractorFactory<T, R> {
    Function<T, R> create(ExtractorType type, Object... args);
}
