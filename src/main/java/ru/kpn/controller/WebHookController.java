package ru.kpn.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bpp.InjectLogger;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.tube.Tube;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class WebHookController {

    private final Tube<Update> tube;

    @InjectLogger
    private Logger<CustomizableLogger.LogLevel> log;

    public WebHookController(Tube<Update> tube) {
        this.tube = tube;
    }

    @RequestMapping(value = "/", method = POST)
    public void update(@RequestBody Update update){
        log.debug("update: {}", update);
        if (update.hasMessage()){
            log.info("text: {}", update.getMessage().getText());
            tube.append(update);
        }
    }
}
