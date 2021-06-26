package ru.kpn.model.tag;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.kpn.model.note.Note;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of Tag")
public class TagTest {

    private final static String NAME = "Tag name";
    private final static String WRONG_NAME = "Wrong tag name";
    private final static Set<Note> NOTES = new HashSet<>(){{add(new Note(0, "0", "0"));}};
    private final static Set<Note> WRONG_NOTES = new HashSet<>(){{add(new Note(1, "1", "1"));}};

    private static Object[][] getInitData(){
        return new Object[][]{
                {NAME, NOTES, true},
                {NAME, WRONG_NOTES, false},
                {WRONG_NAME, NOTES, false},
                {WRONG_NAME, WRONG_NOTES, false}
        };
    }

    private Tag bTag;
    private Tag cTag;

    @BeforeEach
    void setUp() {
        bTag = Tag.builder()
                .name(NAME)
                .notes(NOTES)
                .build();
        cTag = new Tag(NAME, NOTES);
    }

    @Test
    void shouldCompareBTagAndCTag() {
        assertThat(bTag).isEqualTo(cTag);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareCTagAndCTag(String name, Set<Note> notes, boolean expectedResult) {
        Tag tag = new Tag(name, notes);
        assertThat(cTag.equals(tag)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareBTagAndBTag(String name, Set<Note> notes, boolean expectedResult){
        Tag tag = Tag.builder()
                .name(name)
                .notes(notes)
                .build();
        assertThat(bTag.equals(tag)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckNameGetting() {
        Tag tag = Tag.builder().name(NAME).build();
        assertThat(tag.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldCheckNameSetting() {
        Tag tag = Tag.builder().build();
        tag.setName(NAME);
        assertThat(tag.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldCheckNotesGetting() {
        Tag tag = Tag.builder().notes(NOTES).build();
        assertThat(tag.getNotes()).isEqualTo(NOTES);
    }

    @Test
    void shouldTestNotesSetting() {
        Tag tag = Tag.builder().build();
        tag.setNotes(NOTES);
        assertThat(tag.getNotes()).isEqualTo(NOTES);
    }
}
