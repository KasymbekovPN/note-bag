package ru.kpn.objectExtraction.extractor;

import lombok.RequiredArgsConstructor;
import ru.kpn.objectExtraction.builder.Builder;
import ru.kpn.objectExtraction.result.Result;

import java.util.HashMap;
import java.util.Map;

// TODO: 13.11.2021 test 
@RequiredArgsConstructor
public class YmlExtractor<D, RT> implements Extractor<Result<RT>> {

    private final Map<String, Result<RT>> results = new HashMap<>();

    private final Map<String, D> initData;
    private final Builder<D, RT> builder;

    @Override
    public Result<RT> getOrCreate(String key) {
        if (results.containsKey(key)){
            return results.get(key);
        }

        Result<RT> newResult = attemptCreateNewResult(key);
        results.put(key, newResult);

        return newResult;
    }

    private Result<RT> attemptCreateNewResult(String key) {
        return builder.key(key).datum(initData.get(key)).doScenario().build();
    }
}
