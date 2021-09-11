package ru.kpn.i18n.builder;

public interface MessageBuilder {
    MessageBuilder arg(Object object);
    String build();
}
