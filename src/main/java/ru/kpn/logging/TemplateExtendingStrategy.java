package ru.kpn.logging;

public class TemplateExtendingStrategy implements ExtendingStrategy<String> {

    @Override
    public String execute(String value, Object... extensions) {
        return "[{}] [{}] [{}] [{}] [{}] : " + value;
    }
}
