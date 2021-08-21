package ru.kpn.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bpp.logger.InjectLogger;
import ru.kpn.converter.Update2TubeMessageConverter;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.Tube;

import java.util.function.Function;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class WebHookController {

    private final Tube<TubeMessage, BotApiMethod<?>> tube;
    private final Function<Update, TubeMessage> converter;

    @InjectLogger
    private Logger<CustomizableLogger.LogLevel> log;

    public WebHookController(Tube<TubeMessage, BotApiMethod<?>> tube, Function<Update, TubeMessage> converter) {
        this.tube = tube;
        this.converter = converter;
    }

    @RequestMapping(value = "/", method = POST)
    public void update(@RequestBody Update update){
        log.debug("update: {}", update);
        tube.append(converter.apply(update));
    }
}
