package ru.kpn.repository;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.model.tag.TagEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of TagRepository")
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class TagRepositoryTest {

    private static final String NAME = "Some name";

    @Autowired
    private TagRepository repository;

    private ObjectId id;

    @BeforeEach
    void setUp() {
        TagEntity entity = TagEntity.builder()
                .name(NAME)
                .build();
        repository.save(entity);
        id = entity.getId();
    }

    @Test
    void shouldFindById() {
        Optional<TagEntity> maybeEntity = repository.findById(id);
        assertThat(maybeEntity).isPresent();
        assertThat(maybeEntity.get().getId()).isEqualTo(id);
    }

    @Test
    void shouldFindByName() {
        List<TagEntity> entities = repository.findByName(NAME);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getName()).isEqualTo(NAME);
    }

    @Test
    void shouldDeleteById() {
        repository.deleteById(id);
        assertThat(collectionIsEmpty()).isTrue();
    }

    @Test
    void shouldDeleteByName() {
        repository.deleteByName(NAME);
        assertThat(collectionIsEmpty()).isTrue();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    private boolean collectionIsEmpty() {
        List<TagEntity> all = repository.findAll();
        return all.size() == 0;
    }
}
