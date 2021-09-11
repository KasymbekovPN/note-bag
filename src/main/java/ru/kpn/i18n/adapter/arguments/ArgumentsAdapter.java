package ru.kpn.i18n.adapter.arguments;

public interface ArgumentsAdapter {
    String getCode();
    Object[] adapt(Object[] sourceObjects);
}
