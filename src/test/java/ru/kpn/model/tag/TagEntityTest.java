package ru.kpn.model.tag;

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

@DisplayName("Testing of TagEntity")
public class TagEntityTest {

    private static final ObjectId ID = new ObjectId();
    private static final ObjectId WRONG_ID = new ObjectId();

    private static final String NAME = "Tag name";
    private static final String WRONG_NAME = "Wrong tag name";

    private static final Set<ObjectId> NOTES = new HashSet<>(){{add(new ObjectId());}};
    private static final Set<ObjectId> WRONG_NOTES = new HashSet<>(){{add(new ObjectId());}};

    private static Object[][] getInitData(){
        return new TestDataFormer()
                .append(ID, WRONG_ID)
                .append(NAME, WRONG_NAME)
                .append(NOTES, WRONG_NOTES)
                .form();
    }

    private TagEntity bTagEntity;
    private TagEntity cTagEntity;

    @BeforeEach
    void setUp() {
        bTagEntity = TagEntity.builder()
                .id(ID)
                .name(NAME)
                .notes(NOTES)
                .build();
        cTagEntity = new TagEntity(ID, NAME, NOTES);
    }

    @Test
    void shouldCompareBTagAndCTag() {
        assertThat(bTagEntity).isEqualTo(cTagEntity);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareCTagAndCTag(ObjectId id, String name, Set<ObjectId> notes, boolean expectedResult) {
        TagEntity tagEntity = new TagEntity(id, name, notes);
        assertThat(cTagEntity.equals(tagEntity)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareBTagAndBTag(ObjectId id, String name, Set<ObjectId> notes, boolean expectedResult){
        TagEntity tagEntity = TagEntity.builder()
                .id(ID)
                .name(name)
                .notes(notes)
                .build();
        assertThat(bTagEntity.equals(tagEntity)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckIdGetting() {
        TagEntity tagEntity = TagEntity.builder().id(ID).build();
        assertThat(tagEntity.getId()).isEqualTo(ID);
    }

    @Test
    void shouldCheckIdSetting() {
        TagEntity tagEntity = TagEntity.builder().build();
        tagEntity.setId(ID);
        assertThat(tagEntity.getId()).isEqualTo(ID);
    }

    @Test
    void shouldCheckNameGetting() {
        TagEntity tagEntity = TagEntity.builder().name(NAME).build();
        assertThat(tagEntity.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldCheckNameSetting() {
        TagEntity tagEntity = TagEntity.builder().build();
        tagEntity.setName(NAME);
        assertThat(tagEntity.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldCheckNotesGetting() {
        TagEntity tagEntity = TagEntity.builder().notes(NOTES).build();
        assertThat(tagEntity.getNotes()).isEqualTo(NOTES);
    }

    @Test
    void shouldTestNotesSetting() {
        TagEntity tagEntity = TagEntity.builder().build();
        tagEntity.setNotes(NOTES);
        assertThat(tagEntity.getNotes()).isEqualTo(NOTES);
    }
}
