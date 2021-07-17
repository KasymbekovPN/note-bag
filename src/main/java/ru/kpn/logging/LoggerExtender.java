package ru.kpn.logging;

public interface LoggerExtender<T> {
    Object[] extendArgs(Object[] args, Object... extension);
    String extendTemplate(String template);
}
