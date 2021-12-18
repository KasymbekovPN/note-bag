package ru.kpn.i18n.adapter.arguments;

import ru.kpn.i18n.adapter.argument.ArgumentAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ArgumentsAdapterImpl implements ArgumentsAdapter {

    private final String code;
    private final Function<Object[], Object[]> resizer;
    private final Map<Integer, ArgumentAdapter> adapters;

    public static Builder builder(){
        return new Builder();
    }

    private ArgumentsAdapterImpl(String code, Function<Object[], Object[]> resizer, Map<Integer, ArgumentAdapter> adapters) {
        this.code = code;
        this.resizer = resizer;
        this.adapters = adapters;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Object[] adapt(Object[] sourceObjects) {
        Object[] resultObjects = resizer.apply(sourceObjects);
        for (int i = 0; i < resultObjects.length; i++) {
            if (adapters.containsKey(i)){
                resultObjects[i] = adapters.get(i).adapt(resultObjects[i]);
            }
        }

        return resultObjects;
    }

    public static class Builder{
        private String code;
        private Function<Object[], Object[]> resizer;
        private final Map<Integer, ArgumentAdapter> adapters = new HashMap<>();

        public Builder code(String code){
            this.code = code;
            return this;
        }

        public Builder resizer(Function<Object[], Object[]> resizer){
            this.resizer = resizer;
            return this;
        }

        public Builder adapter(int idx, ArgumentAdapter adapter){
            adapters.put(idx, adapter);
            return this;
        }

        public ArgumentsAdapterImpl build(){
            return new ArgumentsAdapterImpl(code, resizer, adapters);
        }
    }
}
