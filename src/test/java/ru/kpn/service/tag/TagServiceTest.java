package ru.kpn.service.tag;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.model.tag.TagEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class TagServiceTest {

    private static final String NAME = "Some name";

    @Autowired
    private TagService service;

    private ObjectId id;

    @BeforeEach
    void setUp() {
        TagEntity entity = TagEntity.builder()
                .name(NAME)
                .build();
        service.save(entity);

        id = entity.getId();
    }

    @Test
    void shouldSaveEntity() {
        TagEntity entity = TagEntity.builder().build();
        service.save(entity);
        assertThat(entity.getId()).isNotNull();
    }

    @Test
    void shouldGetEntityById() {
        Optional<TagEntity> maybeEntity = service.getById(id);
        assertThat(maybeEntity).isPresent();
    }

    @Test
    void shouldGetAllEntities() {
        List<TagEntity> entities = service.getAll();
        assertThat(entities.size()).isNotZero();
    }

    @Test
    void shouldDeleteAllEntities() {
        service.deleteAll();
        List<TagEntity> entities = service.getAll();
        assertThat(entities.size()).isZero();
    }

    @Test
    void shouldDeleteEntityById() {
        service.deleteById(id);
        Optional<TagEntity> maybeEntity = service.getById(id);
        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldGetEntityByName() {
        List<TagEntity> entities = service.getByName(NAME);
        assertThat(entities.size()).isNotZero();
    }

    @Test
    void shouldDeleteEntityByName() {
        service.deleteByName(NAME);
        List<TagEntity> entities = service.getByName(NAME);
        assertThat(entities.size()).isZero();
    }

    @AfterEach
    void tearDown() {
        service.deleteAll();
    }
}
