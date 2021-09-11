package ru.kpn.i18n.adapter.arguments;

public interface ArgumentsAdapter {
    String getCode();
    Boolean matchCode(String code);
    Object[] adapt(Object[] sourceObjects);
}
