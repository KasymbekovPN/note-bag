package ru.kpn.i18n.adapter.argument;

public class SimpleNameClassArgumentAdapter implements ArgumentAdapter {

    private static final String DEFAULT_RESULT = "null";

    @Override
    public Object adapt(Object o) {
        return o == null || !o.getClass().equals(Class.class) ? DEFAULT_RESULT : ((Class<?>)o).getSimpleName();
    }
}
