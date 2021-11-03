package ru.kpn.extractor;

import java.util.function.Function;

public interface ExtractorFactory<T, R> {
    Function<T, R> create(ExtractorType type, Object... args);
}
