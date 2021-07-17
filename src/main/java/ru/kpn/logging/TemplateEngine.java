package ru.kpn.logging;

public interface TemplateEngine {
    String fill(String template, Object... args);
}
