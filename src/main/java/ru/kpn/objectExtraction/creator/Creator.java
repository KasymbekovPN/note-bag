package ru.kpn.objectExtraction.creator;

import ru.kpn.objectExtraction.result.Result;

public interface Creator<D, RT> {
    Result<RT> create(D datum);
}
