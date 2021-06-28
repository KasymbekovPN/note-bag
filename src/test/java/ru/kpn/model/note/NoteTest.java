package ru.kpn.model.note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.kpn.model.tag.Tag;
import ru.kpn.model.userProfile.UserProfile;
import utils.TestDataFormer;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of Note")
public class NoteTest {

    private static final int TYPE = 1;
    private static final int WRONG_TYPE = 2;

    private static final String NAME = "Note name";
    private static final String WRONG_NAME = "Wrong note name";

    private static final String CONTENT = "Note content";
    private static final String WRONG_CONTENT = "Wrong note content";

    private static final UserProfile USER_PROFILE = UserProfile.builder().firstName("name").build();
    private static final UserProfile WRONG_USER_PROFILE = UserProfile.builder().firstName("wrong name").build();

    private static final Set<Tag> TAGS = new HashSet<>(){{add(Tag.builder().name("name").build());}};
    private static final Set<Tag> WRONG_TAGS = new HashSet<>(){{add(Tag.builder().name("wrong name").build());}};

    private static Object[][] getInitData(){
        return new TestDataFormer()
                .append(TAGS, WRONG_TAGS)
                .append(USER_PROFILE, WRONG_USER_PROFILE)
                .append(TYPE, WRONG_TYPE)
                .append(NAME, WRONG_NAME)
                .append(CONTENT, WRONG_CONTENT)
                .form();
    }

    private Note bNote;
    private Note cNote;

    @BeforeEach
    void setUp() {
        this.bNote = Note.builder()
                .type(TYPE)
                .name(NAME)
                .content(CONTENT)
                .userProfile(USER_PROFILE)
                .tags(TAGS)
                .build();
        this.cNote = new Note(TYPE, NAME, CONTENT, USER_PROFILE, TAGS);
    }

    @Test
    void shouldCompareCNoteAndBNote() {
        assertThat(cNote).isEqualTo(bNote);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareCNoteAndCNote(Set<Tag> tags, UserProfile userProfile, int type, String name, String content, boolean expectedResult) {
        Note note = new Note(type, name, content, userProfile, tags);
        assertThat(cNote.equals(note)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareBNoteAndBNote(Set<Tag> tags, UserProfile userProfile, int type, String name, String content, boolean expectedResult) {
        Note note = Note.builder()
                .type(type)
                .name(name)
                .content(content)
                .userProfile(userProfile)
                .tags(tags)
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

    @Test
    void shouldCheckUserGetting() {
        Note note = Note.builder().userProfile(USER_PROFILE).build();
        assertThat(note.getUserProfile()).isEqualTo(USER_PROFILE);
    }

    @Test
    void shouldCheckUserSetting() {
        Note note = Note.builder().build();
        note.setUserProfile(USER_PROFILE);
        assertThat(note.getUserProfile()).isEqualTo(USER_PROFILE);
    }

    @Test
    void shouldCheckTagsGetting() {
        Note note = Note.builder().tags(TAGS).build();
        assertThat(note.getTags()).isEqualTo(TAGS);
    }

    @Test
    void shouldCheckTagsSetting() {
        Note note = Note.builder().build();
        note.setTags(TAGS);
        assertThat(note.getTags()).isEqualTo(TAGS);
    }

}
