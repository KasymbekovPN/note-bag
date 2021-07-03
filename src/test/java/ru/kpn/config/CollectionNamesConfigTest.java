package ru.kpn.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Testing of CollectionNamesConfig")
@SpringBootTest
public class CollectionNamesConfigTest {

    @Autowired
    private CollectionNamesConfig collectionNamesConfig;

    @Value("${mongo.collectionNames.note}")
    private String noteCollectionName;

    @Value("${mongo.collectionNames.tag}")
    private String tagCollectionName;

    @Value("${mongo.collectionNames.user}")
    private String userCollectionName;

    @Value("#{collectionNamesConfig.getNoteCollectionName}")
    private String v;

    @Test
    void shouldCheckNoteCollectionName() {
        assertThat(noteCollectionName).isEqualTo(collectionNamesConfig.getNoteCollectionName());
    }

    @Test
    void shouldCheckTagCollectionName() {
        assertThat(tagCollectionName).isEqualTo(collectionNamesConfig.getTagCollectionName());
    }

    @Test
    void shouldCheckUserCollectionName() {
        assertThat(userCollectionName).isEqualTo(collectionNamesConfig.getUserCollectionName());
    }

    //< 
    @Test
    void name() {
        log.info("--- v = {}", v);
    }
}
