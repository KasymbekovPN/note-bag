package ru.kpn.strategy.injectors;

import lombok.RequiredArgsConstructor;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.datum.Datum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

abstract public class BaseInjector<D extends Datum<? extends DatumType>, RT> implements Injector<D, RT>{

    @Override
    public Result<RT, RawMessage<String>> inject(Object object, String name) {
        return createInnerInjector(object, name)
                .findMethod()
                .datum(getInitData())
                .create(getFactory())
                .inject()
                .get();
    }

    protected abstract ObjectFactory<D,RT, RawMessage<String>> getFactory();
    protected abstract Map<String,D> getInitData();
    abstract protected InnerInjector<D, RT> createInnerInjector(Object object, String name);

    @RequiredArgsConstructor
    protected static class InnerInjector<D extends Datum<? extends DatumType>, RT>{
        private final Object object;
        private final String name;
        private final InjectionType type;

        private boolean success = true;
        private boolean continueIt = true;
        private RawMessage<String> status;
        private Method method;

        private RT value;
        private D datum;

        public InnerInjector<D, RT> findMethod(){
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

        public InnerInjector<D, RT> datum(Map<String, D> initData) {
            if (continueIt){
                if (initData.containsKey(name)){
                    datum = initData.get(name);
                } else {
                    success = false;
                    continueIt = false;
                    status = new BotRawMessage("injection.no.init-data").add(name).add(type);
                }
            }
            return this;
        }

        public InnerInjector<D, RT> create(ObjectFactory<D, RT, RawMessage<String>> factory) {
            if (continueIt){
                Result<RT, RawMessage<String>> result = factory.create(datum);
                continueIt = success = result.getSuccess();
                value = result.getValue();
                status = result.getStatus();
            }
            return this;
        }

        public InnerInjector<D, RT> inject(){
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

        public Result<RT, RawMessage<String>> get(){
            return  new ValuedResult<>(success, value, status);
        }
    }
}
