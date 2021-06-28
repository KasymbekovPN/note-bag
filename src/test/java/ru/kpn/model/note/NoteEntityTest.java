package ru.kpn.model.note;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of NoteEntity")
public class NoteEntityTest {

    private static final ObjectId ID = new ObjectId();
    private static final ObjectId WRONG_ID = new ObjectId();

    private static final Integer TYPE = 0;
    private static final Integer WRONG_TYPE = 1;

    private static final ObjectId USER_ID = new ObjectId();
    private static final ObjectId WRONG_USER_ID = new ObjectId();

    private static final String NAME = "name";
    private static final String WRONG_NAME = "wrong name";

    private static final String CONTENT = "content";
    private static final String WRONG_CONTENT = "wrong content";

    private static final Set<ObjectId> TAGS = new HashSet<>(){{add(new ObjectId());}};
    private static final Set<ObjectId> WRONG_TAGS = new HashSet<>(){{add(new ObjectId());}};

    private static final int SIZE = 64;

    private static Object[][] getInitData(){
        Object[][] objects = new Object[SIZE][7];

        for (int i = 0; i < SIZE; i++) {
            objects[i][0] = i % 2 == 0  ? ID : WRONG_ID;
            objects[i][1] = i % 4 == 0  ? TYPE : WRONG_TYPE;
            objects[i][2] = i % 8 == 0  ? USER_ID : WRONG_USER_ID;
            objects[i][3] = i % 16 == 0 ? NAME : WRONG_NAME;
            objects[i][4] = i % 32 == 0 ? CONTENT : WRONG_CONTENT;
            objects[i][5] = i % 64 == 0 ? TAGS : WRONG_TAGS;
            objects[i][6] = i == 0;
        }

        return objects;
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
                          ObjectId userId,
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
                          ObjectId userId,
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
