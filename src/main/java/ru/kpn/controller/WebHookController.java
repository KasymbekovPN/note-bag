package ru.kpn.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bpp.logger.InjectLogger;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.tube.Tube;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class WebHookController {

    // TODO: 26.08.2021 through spec interface
    private final Tube<Update> tube;

    @InjectLogger
    private Logger<CustomizableLogger.LogLevel> log;

    public WebHookController(Tube<Update> tube) {
        this.tube = tube;
    }

    @RequestMapping(value = "/", method = POST)
    public void update(@RequestBody Update update){
        log.debug("update: {}", update);
        tube.append(update);
    }
}
