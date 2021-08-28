package ru.kpn.controller;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.logging.*;
import ru.kpn.tube.Tube;
import utils.UpdateInstanceBuilder;

import java.util.ArrayList;
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
        tube = new TestTube();
        controller = new WebHookController(tube);
        CustomizableLogger logger = CustomizableLogger.builder(WebHookController.class, CustomizableLoggerSettings.builder().build()).build();
        ReflectionTestUtils.setField(controller, "log", logger);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void shouldDoTubeFillingAndCheckItsSize(int size) {

        for (int i = 0; i < size; i++) {
            controller.update(new UpdateInstanceBuilder().build());
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
