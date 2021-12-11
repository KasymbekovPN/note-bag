package ru.kpn.strategy.injectors;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

// TODO: 11.12.2021 make base class
@Component
@Setter
@ConfigurationProperties(prefix = "telegram.tube")
public class PriorityInjector implements Injector<StrategyInitDatum, Integer> {

    @Autowired
    private ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> strategyInitFactory;
    private Map<String, StrategyInitDatum> strategyInitData;

    @Override
    public Result<Integer, RawMessage<String>> inject(Object object, String name) {
        return new InnerInjector(object, name, InjectionType.PRIORITY)
                .findMethod()
                .datum(strategyInitData)
                .create(strategyInitFactory)
                .inject()
                .get();
    }

    @RequiredArgsConstructor
    private static class InnerInjector{
        private final Object object;
        private final String name;
        private final InjectionType type;

        private boolean success = true;
        private boolean continueIt = true;
        private RawMessage<String> status;
        private Integer value;
        private Method method;
        private StrategyInitDatum datum;

        public InnerInjector findMethod() {
            if (continueIt){
                for (Method declaredMethod : object.getClass().getDeclaredMethods()) {
                    if (declaredMethod.isAnnotationPresent(Inject.class)){
                        Inject annotation = declaredMethod.getAnnotation(Inject.class);
                        if (type.equals(annotation.value())){
                            method = declaredMethod;
                            return this;
                        }
                    }
                }
            }
            continueIt = false;
            status = new BotRawMessage("injection.no.method").add(name).add(type);
            return this;
        }

        public InnerInjector datum(Map<String, StrategyInitDatum> initData) {
            if (continueIt){
                if (initData.containsKey(name)){
                    datum = initData.get(name);
                } else {
                    success = false;
                    continueIt = false;
                    status = new BotRawMessage("injection.no.init-data").add(name).add(InjectionType.PRIORITY);
                }
            }
            return this;
        }

        public InnerInjector create(ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> factory) {
            if (continueIt){
                Result<Integer, RawMessage<String>> result = factory.create(datum);
                continueIt = success = result.getSuccess();
                value = result.getValue();
                status = result.getStatus();
            }
            return this;
        }

        public InnerInjector inject(){
            if (continueIt){
                try {
                    method.invoke(object, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    success = false;
                    status = new BotRawMessage("injection.invoking.fail").add(name).add(type);
                }
            }
            return this;
        }

        public Result<Integer, RawMessage<String>> get(){
            return  new ValuedResult<>(success, value, status);
        }
    }
}
