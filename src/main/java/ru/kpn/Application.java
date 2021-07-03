package ru.kpn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.kpn.model.note.NoteEntity;

@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Application started...");

        //<
        log.info("-- {}", NoteEntity.class.getAnnotation(Document.class).value());
    }
}
