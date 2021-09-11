package ru.kpn.i18n.builder;

import lombok.AllArgsConstructor;
import ru.kpn.i18n.I18n;
import ru.kpn.i18n.adapter.arguments.ArgumentsAdapter;

import java.util.HashMap;
import java.util.Map;

public class MessageBuilderFactoryImpl implements MessageBuilderFactory {

    private final I18n i18n;
    private final Map<String, ArgumentsAdapter> adapters;

    public static Builder builder(){
        return new Builder();
    }

    private MessageBuilderFactoryImpl(I18n i18n, Map<String, ArgumentsAdapter> adapters) {
        this.i18n = i18n;
        this.adapters = adapters;
    }

    @Override
    public MessageBuilder create(String code) {
        ArgumentsAdapter argumentsAdapter = adapters.containsKey(code) ? adapters.get(code) : new DefaultArgumentsAdapter(code);
        return new MessageBuilderImpl(code, i18n, argumentsAdapter);
    }

    @AllArgsConstructor
    public static class DefaultArgumentsAdapter implements ArgumentsAdapter{

        private final String code;

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public Object[] adapt(Object[] sourceObjects) {
            return sourceObjects;
        }
    }

    public static class Builder{
        private final Map<String, ArgumentsAdapter> adapters = new HashMap<>();

        private I18n i18n;

        public Builder i18n(I18n i18n){
            this.i18n = i18n;
            return this;
        }

        public Builder adapter(ArgumentsAdapter adapter){
            this.adapters.put(adapter.getCode(), adapter);
            return this;
        }

        public MessageBuilderFactoryImpl build(){
            return new MessageBuilderFactoryImpl(i18n, adapters);
        }
    }
}
