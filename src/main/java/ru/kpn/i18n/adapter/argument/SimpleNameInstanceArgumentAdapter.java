package ru.kpn.i18n.adapter.argument;

public class SimpleNameInstanceArgumentAdapter implements ArgumentAdapter {

    private static final String DEFAULT_RESULT = "null";

    @Override
    public Object adapt(Object o) {
        return o == null ? DEFAULT_RESULT : o.getClass().getSimpleName();
    }
}
