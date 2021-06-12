package ru.kpn.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.Tube;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@AllArgsConstructor
@RestController
public class WebHookController {

    private final Tube<Update> tube;

    @RequestMapping(value = "/", method = POST)
    public void update(@RequestBody Update update){
        log.debug("update: {}", update);
        if (update.hasMessage()){
            log.info("text: {}", update.getMessage().getText());
            tube.append(update);
        }
    }
}
