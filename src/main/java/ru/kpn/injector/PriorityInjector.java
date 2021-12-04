package ru.kpn.injector;

import lombok.AllArgsConstructor;
import ru.kpn.objectExtraction.datum.StrategyInitDatum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

import java.lang.reflect.Method;

// TODO: 04.12.2021 ???
@AllArgsConstructor
public class PriorityInjector /*implements Injector<Integer, StrategyInitDatum>*/{
    private final Object object;
    private final StrategyInitDatum datum;
    //<
//    private final Result<Integer, RawMessage<String>> result;

    private Method method;
    private Integer value;




//    @Override
//    public Result<Integer, RawMessage<String>> inject(ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> factory) {
//        checkObject();
//        checkDatum();
//        Method method = getMethod();
//        Integer value = createValue(factory);
//        invoke();
////        method.invoke(object, value);
//    }
    //<
    //            Optional<Method> maybeExtractorInjectionMethod = getMethodForInjection(strategy, InjectionType.EXTRACTOR);
}
