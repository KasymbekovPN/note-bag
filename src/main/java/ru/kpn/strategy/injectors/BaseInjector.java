package ru.kpn.strategy.injectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.datum.Datum;
import ru.kpn.objectFactory.factory.ObjectFactory;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactoryOld;
import ru.kpn.strategy.calculaters.nameCalculator.NameCalculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

abstract public class BaseInjector<D extends Datum<? extends DatumType>, RT> implements Injector<D, RT>{

    @Autowired
    private NameCalculator nameCalculator;

    @Override
    public Result<RT, Seed<String>> inject(Object object) {
        return createInnerInjector(object)
                .calculateName(nameCalculator)
                .findMethod()
                .datum(getInitData())
                .create(getFactory())
                .inject()
                .get();
    }

    protected abstract ObjectFactory<D,RT, Seed<String>> getFactory();
    protected abstract Map<String,D> getInitData();
    abstract protected InnerInjector<D, RT> createInnerInjector(Object object);

    @RequiredArgsConstructor
    protected static class InnerInjector<D extends Datum<? extends DatumType>, RT>{
        private final Object object;
        private final InjectionType type;

        private boolean success = true;
        private boolean continueIt = true;
        private Seed<String> status;
        private Method method;
        private String name;

        private RT value;
        private D datum;

        public InnerInjector<D, RT> calculateName(NameCalculator nameCalculator){
            if (continueIt){
                Result<String, Seed<String>> result = nameCalculator.apply(object);
                if (result.getSuccess()){
                    name = result.getValue();
                } else {
                    success = continueIt = false;
                    status = StringSeedBuilderFactoryOld.builder()
                            .code("injection.name.wrong")
                            .arg(type)
                            .arg(object.getClass().getSimpleName())
                            .build();
                }
            }
            return this;
        }

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
                continueIt = false;
                status = StringSeedBuilderFactoryOld.builder().code("injection.no.method").arg(name).arg(type).build();
            }
            return this;
        }

        public InnerInjector<D, RT> datum(Map<String, D> initData) {
            if (continueIt){
                if (initData.containsKey(name)){
                    datum = initData.get(name);
                } else {
                    success = continueIt = false;
                    status = StringSeedBuilderFactoryOld.builder().code("injection.no.init-data").arg(name).arg(type).build();
                }
            }
            return this;
        }

        public InnerInjector<D, RT> create(ObjectFactory<D, RT, Seed<String>> factory) {
            if (continueIt){
                Result<RT, Seed<String>> result = factory.create(datum);
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
                    status = StringSeedBuilderFactoryOld.builder().code("injection.invoking.fail").arg(name).arg(type).build();
                }
            }
            return this;
        }

        public Result<RT, Seed<String>> get(){
            return  new ValuedResult<>(success, value, status);
        }
    }
}
