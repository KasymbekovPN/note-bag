package ru.kpn.model.userProfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.kpn.model.note.Note;
import utils.TestDataFormer;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of UserProfile")
public class UserProfileTest {

    private static final String FIRST_NAME = "First name";
    private static final String WRONG_FIRST_NAME = "Wrong first name";

    private static final String LAST_NAME = "Last name";
    private static final String WRONG_LAST_NAME = "Wrong last name";

    private static final String USER_NAME = "User name";
    private static final String WRONG_USER_NAME = "Wrong user name";

    private static final String LANGUAGE_CODE = "Lang code";
    private static final String WRONG_LANGUAGE_CODE = "Wrong lang code";

    private static final Boolean IS_BOT = true;
    private static final Boolean WRONG_IS_BOT = false;

    private static final Boolean CAN_JOIN_GROUPS = true;
    private static final Boolean WRONG_CAN_JOIN_GROUPS = false;

    private static final Boolean CAN_READ_ALL_GROUP_MESSAGES = true;
    private static final Boolean WRONG_CAN_READ_ALL_GROUP_MESSAGES = false;

    private static final Boolean SUPPORTS_INLINE_QUERIES = true;
    private static final Boolean WRONG_SUPPORTS_INLINE_QUERIES = false;

    private static final int STATE = 0;
    private static final int WRONG_STATE = 1;

    private static final Set<Note> NOTES = new HashSet<>(){{add(new Note(0, "0", "0", null, null));}};
    private static final Set<Note> WRONG_NOTES = new HashSet<>(){{add(new Note(1, "1", "1", null, null));}};

    private static Object[][] getInitData(){
        return new TestDataFormer()
                .append(FIRST_NAME, WRONG_FIRST_NAME)
                .append(LAST_NAME, WRONG_LAST_NAME)
                .append(USER_NAME, WRONG_USER_NAME)
                .append(LANGUAGE_CODE, WRONG_LANGUAGE_CODE)
                .append(IS_BOT, WRONG_IS_BOT)
                .append(CAN_JOIN_GROUPS, WRONG_CAN_JOIN_GROUPS)
                .append(CAN_READ_ALL_GROUP_MESSAGES, WRONG_CAN_READ_ALL_GROUP_MESSAGES)
                .append(SUPPORTS_INLINE_QUERIES, WRONG_SUPPORTS_INLINE_QUERIES)
                .append(STATE, WRONG_STATE)
                .append(NOTES, WRONG_NOTES)
                .form();
    }

    private UserProfile cUserProfile;
    private UserProfile bUserProfile;

    @BeforeEach
    void setUp() {
        cUserProfile = new UserProfile(
                FIRST_NAME,
                LAST_NAME,
                USER_NAME,
                LANGUAGE_CODE,
                IS_BOT,
                CAN_JOIN_GROUPS,
                CAN_READ_ALL_GROUP_MESSAGES,
                SUPPORTS_INLINE_QUERIES,
                STATE,
                NOTES
        );
        bUserProfile = UserProfile.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .userName(USER_NAME)
                .languageCode(LANGUAGE_CODE)
                .isBot(IS_BOT)
                .canJoinGroups(CAN_JOIN_GROUPS)
                .canReadAllGroupMessages(CAN_READ_ALL_GROUP_MESSAGES)
                .supportsInlineQueries(SUPPORTS_INLINE_QUERIES)
                .state(STATE)
                .notes(NOTES)
                .build();
    }

    @Test
    void shouldCompareBAndC() {
        assertThat(bUserProfile).isEqualTo(cUserProfile);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareBAndB(String firstName,
                            String lastName,
                            String userName,
                            String langCode,
                            Boolean isBot,
                            Boolean canJoin,
                            Boolean canRead,
                            Boolean supp,
                            Integer state,
                            Set<Note> notes,
                            Boolean expectedResult){
        UserProfile up = UserProfile.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .languageCode(langCode)
                .isBot(isBot)
                .canJoinGroups(canJoin)
                .canReadAllGroupMessages(canRead)
                .supportsInlineQueries(supp)
                .state(state)
                .notes(notes)
                .build();
        assertThat(up.equals(bUserProfile)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareCAndC(String firstName,
                            String lastName,
                            String userName,
                            String langCode,
                            Boolean isBot,
                            Boolean canJoin,
                            Boolean canRead,
                            Boolean supp,
                            Integer state,
                            Set<Note> notes,
                            Boolean expectedResult){
        UserProfile up = new UserProfile(
                firstName,
                lastName,
                userName,
                langCode,
                isBot,
                canJoin,
                canRead,
                supp,
                state,
                notes
        );
        assertThat(up.equals(cUserProfile)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFirstNameGetting() {
        UserProfile up = UserProfile.builder().firstName(FIRST_NAME).build();
        assertThat(up.getFirstName()).isEqualTo(FIRST_NAME);
    }

    @Test
    void shouldCheckFirstNameSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setFirstName(FIRST_NAME);
        assertThat(up.getFirstName()).isEqualTo(FIRST_NAME);
    }

    @Test
    void shouldCheckLastNameGetting() {
        UserProfile up = UserProfile.builder().lastName(LAST_NAME).build();
        assertThat(up.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void shouldCheckLastNameSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setLastName(LAST_NAME);
        assertThat(up.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void shouldCheckUserNameGetting() {
        UserProfile up = UserProfile.builder().userName(USER_NAME).build();
        assertThat(up.getUserName()).isEqualTo(USER_NAME);
    }

    @Test
    void shouldCheckUserNameSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setUserName(USER_NAME);
        assertThat(up.getUserName()).isEqualTo(USER_NAME);
    }

    @Test
    void shouldCheckLangCodeGetting() {
        UserProfile up = UserProfile.builder().languageCode(LANGUAGE_CODE).build();
        assertThat(up.getLanguageCode()).isEqualTo(LANGUAGE_CODE);
    }

    @Test
    void shouldCheckLangCodeSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setLanguageCode(LANGUAGE_CODE);
        assertThat(up.getLanguageCode()).isEqualTo(LANGUAGE_CODE);
    }

    @Test
    void shouldCheckBotGetting() {
        UserProfile up = UserProfile.builder().isBot(IS_BOT).build();
        assertThat(up.getIsBot()).isEqualTo(IS_BOT);
    }

    @Test
    void shouldCheckBotSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setIsBot(IS_BOT);
        assertThat(up.getIsBot()).isEqualTo(IS_BOT);
    }

    @Test
    void shouldCheckCanJoinGetting() {
        UserProfile up = UserProfile.builder().canJoinGroups(CAN_JOIN_GROUPS).build();
        assertThat(up.getCanJoinGroups()).isEqualTo(CAN_JOIN_GROUPS);
    }

    @Test
    void shouldCheckCanJoinSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setCanJoinGroups(CAN_JOIN_GROUPS);
        assertThat(up.getCanJoinGroups()).isEqualTo(CAN_JOIN_GROUPS);
    }

    @Test
    void shouldCheckCanReadGetting() {
        UserProfile up = UserProfile.builder().canReadAllGroupMessages(CAN_READ_ALL_GROUP_MESSAGES).build();
        assertThat(up.getCanReadAllGroupMessages()).isEqualTo(CAN_READ_ALL_GROUP_MESSAGES);
    }

    @Test
    void shouldCheckCanReadSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setCanReadAllGroupMessages(CAN_READ_ALL_GROUP_MESSAGES);
        assertThat(up.getCanReadAllGroupMessages()).isEqualTo(CAN_READ_ALL_GROUP_MESSAGES);
    }

    @Test
    void shouldCheckSuppGetting() {
        UserProfile up = UserProfile.builder().supportsInlineQueries(SUPPORTS_INLINE_QUERIES).build();
        assertThat(up.getSupportsInlineQueries()).isEqualTo(SUPPORTS_INLINE_QUERIES);
    }

    @Test
    void shouldCheckSuppSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setSupportsInlineQueries(SUPPORTS_INLINE_QUERIES);
        assertThat(up.getSupportsInlineQueries()).isEqualTo(SUPPORTS_INLINE_QUERIES);
    }

    @Test
    void shouldCheckStateGetting() {
        UserProfile up = UserProfile.builder().state(STATE).build();
        assertThat(up.getState()).isEqualTo(STATE);
    }

    @Test
    void shouldCheckStateSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setState(STATE);
        assertThat(up.getState()).isEqualTo(STATE);
    }

    @Test
    void shouldCheckNotesGetting() {
        UserProfile up = UserProfile.builder().notes(NOTES).build();
        assertThat(up.getNotes()).isEqualTo(NOTES);
    }

    @Test
    void shouldCheckNotesSetting() {
        UserProfile up = UserProfile.builder().build();
        up.setNotes(NOTES);
        assertThat(up.getNotes()).isEqualTo(NOTES);
    }
}
