package ru.kpn.objectFactory.creator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.seed.Seed;
import ru.kpn.seed.SeedBuilder;
import ru.kpn.seed.SeedBuilderService;

abstract public class BaseCreator<T, D, RT> extends AbstractTypedCreator<T, D, RT, Seed<String>>{

    private final T type;

    @Autowired
    protected SeedBuilderService<String> seedBuilderService;

    public BaseCreator(T type) {
        this.type = type;
    }

    @Override
    public T getType() {
        return type;
    }

    @AllArgsConstructor
    abstract public static class BaseBuilder<RT, D> extends AbstractResultBuilder<RT, Seed<String>>{
        protected final D datum;
        protected final String key;
        protected final SeedBuilderService<String> seedBuilderService;

        @Override
        public ResultBuilder<RT, Seed<String>> calculateValue() {
            if (success){
                value = calculateValueImpl();
            }
            return this;
        }

        @Override
        protected Result<RT, Seed<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<RT, Seed<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }

        protected SeedBuilder<String> takeNewSeedBuilder(){
            return seedBuilderService.takeNew();
        }

        protected void setFailStatus(Seed<String> status){
            this.success = false;
            this.status = status;
        }

        protected abstract RT calculateValueImpl();
    }
}
