package ru.kpn.controller;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.logging.*;
import ru.kpn.service.logger.LoggerService;
import ru.kpn.service.logger.LoggerServiceImpl;
import ru.kpn.tube.Tube;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@DisplayName("Test of WebHookController")
public class WebHookControllerTest {

    private static Object[][] getData(){
        return new Object[][]{
                {0},
                {10},
                {100}
        };
    }

    private WebHookController controller;
    private TestTube tube;

    @BeforeEach
    void setUp() {
        CustomizableLoggerSettings setting = CustomizableLoggerSettings.builder()
                .enable(CustomizableLogger.LogLevel.TRACE)
                .enable(CustomizableLogger.LogLevel.DEBUG)
                .enable(CustomizableLogger.LogLevel.INFO)
                .enable(CustomizableLogger.LogLevel.WARN)
                .enable(CustomizableLogger.LogLevel.DEBUG)
                .build();
        HashSet<Writer> writers = new HashSet<>() {{
            add(new SoutWriter());
        }};
        LoggerService<CustomizableLogger.LogLevel> loggerService = new LoggerServiceImpl(setting, writers, new LoggerTemplateEngine(), new ArgsExtendingStrategy(), new TemplateExtendingStrategy());
        tube = new TestTube();
        controller = new WebHookController(tube, loggerService);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void shouldDoTubeFillingAndCheckItsSize(int size) {

        for (int i = 0; i < size; i++) {
            Update update = new Update();
            update.setMessage(new Message());
            controller.update(update);
        }
        Assertions.assertThat(tube.getMessageSize()).isEqualTo(size);
    }

    private static class TestTube implements Tube<Update> {
        private final List<Update> messages = new ArrayList<>();

        @Override
        public boolean append(Update update) {
            messages.add(update);
            return false;
        }

        public int getMessageSize(){
            return messages.size();
        }
    }
}
