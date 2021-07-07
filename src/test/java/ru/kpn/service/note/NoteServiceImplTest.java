package ru.kpn.service.note;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.model.note.NoteEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of NoteServiceImpl")
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class NoteServiceImplTest {

    private static final String NAME = "some name";

    @Autowired
    private NoteService service;

    private ObjectId id;

    @BeforeEach
    void setUp() {
        NoteEntity entity = NoteEntity.builder()
                .name(NAME)
                .build();
        service.save(entity);

        id = entity.getId();
    }

    @Test
    void shouldSaveEntityIntoCollection() {
        NoteEntity entity = NoteEntity.builder().build();
        service.save(entity);
        assertThat(entity.getId()).isNotNull();
    }

    @Test
    void shouldGelAllEntitiesFromCollection() {
        List<NoteEntity> entities = service.getAll();
        assertThat(entities.size()).isNotZero();
    }

    @Test
    void shouldDeleteAllEntitiesFormCollection() {
        service.deleteAll();
        List<NoteEntity> entities = service.getAll();
        assertThat(entities.size()).isZero();
    }

    @Test
    void shouldGetEntityById() {
        Optional<NoteEntity> maybeEntity = service.getById(id);
        assertThat(maybeEntity).isPresent();
    }

    @Test
    void shouldDeleteEntityById() {
        service.deleteById(id);
        Optional<NoteEntity> maybeEntity = service.getById(id);
        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldGetEntityByName() {
        List<NoteEntity> entities = service.getByName(NAME);
        assertThat(entities.size()).isNotZero();
    }

    @Test
    void shouldDeleteEntityByName() {
        service.deleteByName(NAME);
        List<NoteEntity> entities = service.getByName(NAME);
        assertThat(entities.size()).isZero();
    }

    @AfterEach
    void tearDown() {
        service.deleteAll();
    }
}
