package ru.kpn.model.note;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.kpn.config.CollectionNamesConfig;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Testing of NoteEntity")
@SpringBootTest
public class NoteEntityContextTest {

    @Autowired
    private CollectionNamesConfig collectionNamesConfig;

    //<
    @Value("${mongo.collectionNames.note}")
    private String noteCollectionName;

    //<
    @Value("#{collectionNamesConfig.getNoteCollectionName()}")
    private String bbb;

    @Test
    void shouldCheckDocumentValue() {

        log.info(" -- {}", collectionNamesConfig.getNoteCollectionName());
        log.info(" -- {}", bbb);

        String value = NoteEntity.class.getAnnotation(Document.class).value();
        assertThat(value).isEqualTo(noteCollectionName);
    }
}
