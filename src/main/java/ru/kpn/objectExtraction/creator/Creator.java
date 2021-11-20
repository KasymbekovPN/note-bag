package ru.kpn.objectExtraction.creator;

import ru.kpn.objectExtraction.result.Result;

public interface Creator<T> {
    Result<T> create(Object... args);
}
