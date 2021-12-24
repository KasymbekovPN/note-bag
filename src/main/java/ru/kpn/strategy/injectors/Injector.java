package ru.kpn.strategy.injectors;

import ru.kpn.objectFactory.datum.Datum;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.seed.Seed;

public interface Injector<D extends Datum<? extends DatumType>, RT> {
    Result<RT, Seed<String>> inject(Object object);
}
