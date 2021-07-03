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
import ru.kpn.model.note.NoteEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of NoteRepository")
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class NoteRepositoryTest {

    private static final Integer TYPE = 0;
    private static final Integer USER_ID = 1;
    private static final String NAME = "some name";

    @Autowired
    private NoteRepository repository;

    private ObjectId id;

    @BeforeEach
    void setUp() {
        NoteEntity ne = NoteEntity.builder()
                .type(TYPE)
                .userId(USER_ID)
                .name(NAME)
                .build();
        repository.save(ne);
        id = ne.getId();
    }

    @Test
    void shouldCheckSave() {
        NoteEntity ne = NoteEntity.builder().build();
        repository.save(ne);
        assertThat(ne.getId()).isNotNull();
    }

    @Test
    void shouldFindById() {
        Optional<NoteEntity> maybeEntity = repository.findById(id);
        assertThat(maybeEntity).isPresent();
    }

    @Test
    void shouldFindByUserId() {
        List<NoteEntity> entities = repository.findByUserId(USER_ID);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getUserId()).isEqualTo(USER_ID);
    }

    @Test
    void shouldFindByName() {
        List<NoteEntity> entities = repository.findByName(NAME);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getName()).isEqualTo(NAME);
    }

    @Test
    void shouldDeleteById() {
        List<NoteEntity> entities = repository.findAll();
        assertThat(entities.size()).isNotEqualTo(0);
        repository.deleteById(id);
        entities = repository.findAll();
        assertThat(entities.size()).isEqualTo(0);
    }

    @Test
    void shouldDeleteByUserId() {
        List<NoteEntity> entities = repository.findAll();
        assertThat(entities.size()).isNotEqualTo(0);
        repository.deleteByUserId(USER_ID);
        entities = repository.findAll();
        assertThat(entities.size()).isEqualTo(0);
    }

    @Test
    void shouldDeleteByName() {
        List<NoteEntity> entities = repository.findAll();
        assertThat(entities.size()).isNotEqualTo(0);
        repository.deleteByName(NAME);
        entities = repository.findAll();
        assertThat(entities.size()).isEqualTo(0);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }
}
