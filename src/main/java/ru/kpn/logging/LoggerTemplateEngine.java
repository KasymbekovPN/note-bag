package ru.kpn.logging;

class LoggerTemplateEngine implements TemplateEngine {

    @Override
    public String fill(String template, Object... args) {
        String result = template;
        for (Object arg : args) {
            result = result.replaceFirst("\\{}", arg.toString());
        }
        return result;
    }
}
