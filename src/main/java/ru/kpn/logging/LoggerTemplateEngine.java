package ru.kpn.logging;

public class LoggerTemplateEngine implements TemplateEngine {

    @Override
    public String fill(String template, Object... args) {
        String result = template;
        for (Object arg : args) {
            result = result.replaceFirst("\\{}", String.valueOf(arg));
        }
        return result + "\n";
    }
}
