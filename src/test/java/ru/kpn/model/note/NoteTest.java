package ru.kpn.model.note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of Note")
public class NoteTest {

    private static final int TYPE = 1;
    private static final int WRONG_TYPE = 2;
    private static final String NAME = "Note name";
    private static final String WRONG_NAME = "Wrong note name";
    private static final String CONTENT = "Note content";
    private static final String WRONG_CONTENT = "Wrong note content";

    private static Object[][] getInitData(){
        return new Object[][]{
                {TYPE, NAME, CONTENT, true},
                {TYPE, NAME, WRONG_CONTENT, false},
                {TYPE, WRONG_NAME, CONTENT, false},
                {TYPE, WRONG_NAME, WRONG_CONTENT, false},
                {WRONG_TYPE, NAME, CONTENT, false},
                {WRONG_TYPE, NAME, WRONG_CONTENT, false},
                {WRONG_TYPE, WRONG_NAME, CONTENT, false},
                {WRONG_TYPE, WRONG_NAME, WRONG_CONTENT, false}
        };
    }

    private Note bNote;
    private Note cNote;

    @BeforeEach
    void setUp() {
        this.bNote = Note.builder()
                .type(TYPE)
                .name(NAME)
                .content(CONTENT)
                .build();
        this.cNote = new Note(TYPE, NAME, CONTENT);
    }

    @Test
    void shouldCompareCNoteAndBNote() {
        assertThat(cNote).isEqualTo(bNote);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareCNoteAndCNote(int type, String name, String content, boolean expectedResult) {
        Note note = new Note(type, name, content);
        assertThat(cNote.equals(note)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareBNoteAndBNote(int type, String name, String content, boolean expectedResult) {
        Note note = Note.builder()
                .type(type)
                .name(name)
                .content(content)
                .build();
        assertThat(bNote.equals(note)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckNoteTypeGetting() {
        Note note = Note.builder().type(TYPE).build();
        assertThat(note.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldCheckNoteTypeSetting() {
        Note note = Note.builder().build();
        note.setType(TYPE);
        assertThat(note.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldCheckNameGetting() {
        Note note = Note.builder().name(NAME).build();
        assertThat(note.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldCheckNameSetting() {
        Note note = Note.builder().build();
        note.setName(NAME);
        assertThat(note.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldCheckContentGetting() {
        Note note = Note.builder().content(CONTENT).build();
        assertThat(note.getContent()).isEqualTo(CONTENT);
    }

    @Test
    void shouldCheckContentSetting() {
        Note note = Note.builder().build();
        note.setContent(CONTENT);
        assertThat(note.getContent()).isEqualTo(CONTENT);
    }
}
