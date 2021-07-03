package ru.kpn.model.note;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.TestDataFormer;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of NoteEntity")
public class NoteEntityTest {

    private static final ObjectId ID = new ObjectId();
    private static final ObjectId WRONG_ID = new ObjectId();

    private static final Integer TYPE = 0;
    private static final Integer WRONG_TYPE = 1;

    private static final Integer USER_ID = 0;
    private static final Integer WRONG_USER_ID = 1;

    private static final String NAME = "name";
    private static final String WRONG_NAME = "wrong name";

    private static final String CONTENT = "content";
    private static final String WRONG_CONTENT = "wrong content";

    private static final Set<ObjectId> TAGS = new HashSet<>(){{add(new ObjectId());}};
    private static final Set<ObjectId> WRONG_TAGS = new HashSet<>(){{add(new ObjectId());}};

    private static Object[][] getInitData(){
        return new TestDataFormer()
                .append(ID, WRONG_ID)
                .append(TYPE, WRONG_TYPE)
                .append(USER_ID, WRONG_USER_ID)
                .append(NAME, WRONG_NAME)
                .append(CONTENT, WRONG_CONTENT)
                .append(TAGS, WRONG_TAGS)
                .form();
    }

    private NoteEntity bNoteEntity;
    private NoteEntity cNoteEntity;

    @BeforeEach
    void setUp() {
        bNoteEntity = NoteEntity.builder()
                .id(ID)
                .type(TYPE)
                .userId(USER_ID)
                .name(NAME)
                .content(CONTENT)
                .tags(TAGS)
                .build();
        cNoteEntity = new NoteEntity(ID, TYPE, USER_ID, NAME, CONTENT, TAGS);
    }

    @Test
    void shouldCheckBAndC() {
        assertThat(bNoteEntity).isEqualTo(cNoteEntity);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCheckCAndC(ObjectId id,
                          Integer type,
                          Integer userId,
                          String name,
                          String content,
                          Set<ObjectId> tags,
                          Boolean expectedResult) {
        NoteEntity noteEntity = new NoteEntity(id, type, userId, name, content, tags);
        assertThat(noteEntity.equals(cNoteEntity)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCheckBAndB(ObjectId id,
                          Integer type,
                          Integer userId,
                          String name,
                          String content,
                          Set<ObjectId> tags,
                          Boolean expectedResult) {
        NoteEntity noteEntity = NoteEntity.builder()
                .id(id)
                .type(type)
                .userId(userId)
                .name(name)
                .content(content)
                .tags(tags)
                .build();
        assertThat(noteEntity.equals(bNoteEntity)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckIdGetting() {
        NoteEntity noteEntity = NoteEntity.builder().id(ID).build();
        assertThat(noteEntity.getId()).isEqualTo(ID);
    }

    @Test
    void shouldCheckIdSetting() {
        NoteEntity noteEntity = NoteEntity.builder().build();
        noteEntity.setId(ID);
        assertThat(noteEntity.getId()).isEqualTo(ID);
    }

    @Test
    void shouldCheckTypeGetting() {
        NoteEntity noteEntity = NoteEntity.builder().type(TYPE).build();
        assertThat(noteEntity.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldCheckTypeSetting() {
        NoteEntity noteEntity = NoteEntity.builder().build();
        noteEntity.setType(TYPE);
        assertThat(noteEntity.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldCheckUserIdGetting() {
        NoteEntity noteEntity = NoteEntity.builder().userId(USER_ID).build();
        assertThat(noteEntity.getUserId()).isEqualTo(USER_ID);
    }

    @Test
    void shouldCheckUserSetting() {
        NoteEntity noteEntity = NoteEntity.builder().build();
        noteEntity.setUserId(USER_ID);
        assertThat(noteEntity.getUserId()).isEqualTo(USER_ID);
    }

    @Test
    void shouldCheckNameGetting() {
        NoteEntity noteEntity = NoteEntity.builder().name(NAME).build();
        assertThat(noteEntity.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldCheckNameSetting() {
        NoteEntity noteEntity = NoteEntity.builder().build();
        noteEntity.setName(NAME);
        assertThat(noteEntity.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldCheckContentGetting() {
        NoteEntity noteEntity = NoteEntity.builder().content(CONTENT).build();
        assertThat(noteEntity.getContent()).isEqualTo(CONTENT);
    }

    @Test
    void shouldCheckContentSetting() {
        NoteEntity noteEntity = NoteEntity.builder().build();
        noteEntity.setContent(CONTENT);
        assertThat(noteEntity.getContent()).isEqualTo(CONTENT);
    }

    @Test
    void shouldCheckTagsGetting() {
        NoteEntity noteEntity = NoteEntity.builder().tags(TAGS).build();
        assertThat(noteEntity.getTags()).isEqualTo(TAGS);
    }

    @Test
    void shouldCheckTagsSetting() {
        NoteEntity noteEntity = NoteEntity.builder().build();
        noteEntity.setTags(TAGS);
        assertThat(noteEntity.getTags()).isEqualTo(TAGS);
    }
}
