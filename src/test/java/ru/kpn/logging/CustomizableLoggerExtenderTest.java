package ru.kpn.logging;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomizableLoggerExtenderTest {

    private final CustomizableLogger.LogLevel logLevel = CustomizableLogger.LogLevel.TRACE;
    private final LoggerExtender<CustomizableLogger.LogLevel> extender = new CustomizableLoggerExtender();

    @Test
    void shouldExtendArgs() {
        Object[] args = {"Test", " : ", 1};
        Object[] extendedArgs = extender.extendArgs(args, logLevel, this.getClass());

        assertThat(extendedArgs.length).isEqualTo(args.length + 5);
        assertThat(extendedArgs[1]).isEqualTo(logLevel);
        assertThat(extendedArgs[4]).isEqualTo(this.getClass().getSimpleName());
        for (int i = 0; i < args.length; i++) {
            assertThat(args[i]).isEqualTo(extendedArgs[5 + i]);
        }
    }

    @Test
    void shouldExtendTemplate() {
        String separator = " : ";
        String template = "{} {} {}";
        String extension = "[{}] [{}] [{}] [{}] [{}] : ";
        String extendedTemplate = extender.extendTemplate(template);

        String[] split = extendedTemplate.split(separator);
        assertThat(split.length).isEqualTo(2);
        assertThat(split[0] + separator).isEqualTo(extension);
        assertThat(split[1]).isEqualTo(template);
    }
}
