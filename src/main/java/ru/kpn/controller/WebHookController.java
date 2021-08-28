package ru.kpn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.Tube;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
public class WebHookController {
    
    private final Tube<Update> tube;

    public WebHookController(Tube<Update> tube) {
        this.tube = tube;
    }

    @RequestMapping(value = "/", method = POST)
    public void update(@RequestBody Update update){
        log.debug("update: {}", update);
        tube.append(update);
    }
}
