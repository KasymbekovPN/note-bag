package ru.kpn.bpp.logger;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.controller.WebHookController;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.service.logger.LoggerService;
import ru.kpn.service.note.NoteServiceImpl;
import ru.kpn.service.tag.TagServiceImpl;
import ru.kpn.service.userProfile.UserProfileServiceImpl;
import ru.kpn.tube.Tube;
import ru.kpn.tube.runner.TelegramTubeRunner;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 14.08.2021 there is need to take beans automatically
@SpringBootTest
public class LoggerBPPTest {

    @Autowired
    private LoggerService<CustomizableLogger.LogLevel> loggerService;

    @Autowired
    private WebHookController webHookController;

    @Autowired
    private NoteServiceImpl noteService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private UserProfileServiceImpl userProfileService;

    // TODO: 23.08.2021 restore
//    @Autowired
//    private TelegramTubeRunner telegramTubeRunner;
//
//    @Autowired
//    private Tube<Update, BotApiMethod<?>> tube;

    @Test
    void shouldCheckInjectionIntoWebHookController() {
        assertThat(checkLogger(webHookController)).isTrue();
    }

    @Test
    void shouldCheckInjectionIntoNoteService() {
        assertThat(checkLogger(noteService)).isTrue();
    }

    @Test
    void shouldCheckInjectionIntoTagService() {
        assertThat(checkLogger(tagService)).isTrue();
    }

    @Test
    void shouldCheckInjectionIntoUserProfileService() {
        assertThat(checkLogger(userProfileService)).isTrue();
    }

    // TODO: 23.08.2021 restore
//    @Test
//    void shouldCheckInjectionIntoTelegramTubeRunner() {
//        assertThat(checkLogger(telegramTubeRunner)).isTrue();
//    }

    // TODO: 23.08.2021 restore
//    @Test
//    void shouldCheckInjectionIntoTube() {
//        assertThat(checkLogger(tube)).isTrue();
//    }

    @SneakyThrows
    private boolean checkLogger(Object object) {
        Class<?> type = object.getClass();
        Logger<CustomizableLogger.LogLevel> logger = loggerService.create(type);
        Field logField = type.getDeclaredField("log");
        logField.setAccessible(true);
        Object log = logField.get(object);

        return logger.equals(log);
    }
}
