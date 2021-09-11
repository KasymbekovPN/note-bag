package ru.kpn.i18n.builder;

import lombok.AllArgsConstructor;
import ru.kpn.i18n.I18n;
import ru.kpn.i18n.adapter.arguments.ArgumentsAdapter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MessageBuilderImpl implements MessageBuilder {
    private final String code;
    private final I18n i18n;
    private final ArgumentsAdapter argumentsAdapter;
    private final List<Object> arguments = new ArrayList<>();

    @Override
    public MessageBuilder arg(Object object) {
        arguments.add(object);
        return this;
    }

    @Override
    public String build() {
        Object[] adaptedArgs = argumentsAdapter.adapt(arguments.toArray());
        return i18n.get(code, adaptedArgs);
    }
}
