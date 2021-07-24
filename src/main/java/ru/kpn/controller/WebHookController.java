package ru.kpn.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.service.logger.LoggerService;
import ru.kpn.tube.Tube;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@AllArgsConstructor
@RestController
public class WebHookController {

    private final Tube<Update> tube;
    private final LoggerService<CustomizableLogger.LogLevel> loggerService;

    @RequestMapping(value = "/", method = POST)
    public void update(@RequestBody Update update){
        Logger<CustomizableLogger.LogLevel> log = loggerService.create(this.getClass());
        log.debug("update: {}", update);
        if (update.hasMessage()){
            log.info("text: {}", update.getMessage().getText());
            tube.append(update);
        }
    }
}
