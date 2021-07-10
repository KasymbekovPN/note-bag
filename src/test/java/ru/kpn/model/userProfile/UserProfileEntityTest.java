package ru.kpn.model.userProfile;

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

@DisplayName(("Testing of UserProfileEntity"))
public class UserProfileEntityTest {

    private static final ObjectId ID = new ObjectId();
    private static final ObjectId WRONG_ID = new ObjectId();

    private static final Integer USER_ID = 123;
    private static final Integer WRONG_USER_ID = 123;

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

    private static final Set<ObjectId> NOTES = new HashSet<>(){{add(new ObjectId());}};
    private static final Set<ObjectId> WRONG_NOTES = new HashSet<>(){{add(new ObjectId());}};

    private static Object[][] getInitData(){
        return new TestDataFormer()
                .append(ID, WRONG_ID)
                .append(USER_ID, WRONG_USER_ID)
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

    private UserProfileEntity bUserProfileEntity;
    private UserProfileEntity cUserProfileEntity;

    @BeforeEach
    void setUp() {
        cUserProfileEntity = new UserProfileEntity(
                ID,
                USER_ID,
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
        bUserProfileEntity = UserProfileEntity.builder()
                .id(ID)
                .userId(USER_ID)
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
        assertThat(bUserProfileEntity).isEqualTo(cUserProfileEntity);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareBAndB(ObjectId id,
                            Integer userId,
                            String firstName,
                            String lastName,
                            String userName,
                            String langCode,
                            Boolean isBot,
                            Boolean canJoin,
                            Boolean canRead,
                            Boolean supp,
                            Integer state,
                            Set<ObjectId> notes,
                            Boolean expectedResult){
        UserProfileEntity up = UserProfileEntity.builder()
                .id(id)
                .userId(userId)
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
        assertThat(up.equals(bUserProfileEntity)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareCAndC(ObjectId id,
                            Integer userId,
                            String firstName,
                            String lastName,
                            String userName,
                            String langCode,
                            Boolean isBot,
                            Boolean canJoin,
                            Boolean canRead,
                            Boolean supp,
                            Integer state,
                            Set<ObjectId> notes,
                            Boolean expectedResult){
        UserProfileEntity up = new UserProfileEntity(
                id,
                userId,
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
        assertThat(up.equals(cUserProfileEntity)).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckIdGetting() {
        UserProfileEntity up = UserProfileEntity.builder().id(ID).build();
        assertThat(up.getId()).isEqualTo(ID);
    }

    @Test
    void shouldCheckIdSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setId(ID);
        assertThat(up.getId()).isEqualTo(ID);
    }

    @Test
    void shouldGetUserId() {
        UserProfileEntity entity = UserProfileEntity.builder().userId(USER_ID).build();
        assertThat(entity.getUserId()).isEqualTo(USER_ID);
    }

    @Test
    void shouldSetUserId() {
        UserProfileEntity entity = UserProfileEntity.builder().build();
        entity.setUserId(USER_ID);
        assertThat(entity.getUserId()).isEqualTo(USER_ID);
    }

    @Test
    void shouldCheckFirstNameGetting() {
        UserProfileEntity up = UserProfileEntity.builder().firstName(FIRST_NAME).build();
        assertThat(up.getFirstName()).isEqualTo(FIRST_NAME);
    }

    @Test
    void shouldCheckFirstNameSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setFirstName(FIRST_NAME);
        assertThat(up.getFirstName()).isEqualTo(FIRST_NAME);
    }

    @Test
    void shouldCheckLastNameGetting() {
        UserProfileEntity up = UserProfileEntity.builder().lastName(LAST_NAME).build();
        assertThat(up.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void shouldCheckLastNameSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setLastName(LAST_NAME);
        assertThat(up.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void shouldCheckUserNameGetting() {
        UserProfileEntity up = UserProfileEntity.builder().userName(USER_NAME).build();
        assertThat(up.getUserName()).isEqualTo(USER_NAME);
    }

    @Test
    void shouldCheckUserNameSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setUserName(USER_NAME);
        assertThat(up.getUserName()).isEqualTo(USER_NAME);
    }

    @Test
    void shouldCheckLangCodeGetting() {
        UserProfileEntity up = UserProfileEntity.builder().languageCode(LANGUAGE_CODE).build();
        assertThat(up.getLanguageCode()).isEqualTo(LANGUAGE_CODE);
    }

    @Test
    void shouldCheckLangCodeSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setLanguageCode(LANGUAGE_CODE);
        assertThat(up.getLanguageCode()).isEqualTo(LANGUAGE_CODE);
    }

    @Test
    void shouldCheckBotGetting() {
        UserProfileEntity up = UserProfileEntity.builder().isBot(IS_BOT).build();
        assertThat(up.getIsBot()).isEqualTo(IS_BOT);
    }

    @Test
    void shouldCheckBotSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setIsBot(IS_BOT);
        assertThat(up.getIsBot()).isEqualTo(IS_BOT);
    }

    @Test
    void shouldCheckCanJoinGetting() {
        UserProfileEntity up = UserProfileEntity.builder().canJoinGroups(CAN_JOIN_GROUPS).build();
        assertThat(up.getCanJoinGroups()).isEqualTo(CAN_JOIN_GROUPS);
    }

    @Test
    void shouldCheckCanJoinSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setCanJoinGroups(CAN_JOIN_GROUPS);
        assertThat(up.getCanJoinGroups()).isEqualTo(CAN_JOIN_GROUPS);
    }

    @Test
    void shouldCheckCanReadGetting() {
        UserProfileEntity up = UserProfileEntity.builder().canReadAllGroupMessages(CAN_READ_ALL_GROUP_MESSAGES).build();
        assertThat(up.getCanReadAllGroupMessages()).isEqualTo(CAN_READ_ALL_GROUP_MESSAGES);
    }

    @Test
    void shouldCheckCanReadSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setCanReadAllGroupMessages(CAN_READ_ALL_GROUP_MESSAGES);
        assertThat(up.getCanReadAllGroupMessages()).isEqualTo(CAN_READ_ALL_GROUP_MESSAGES);
    }

    @Test
    void shouldCheckSuppGetting() {
        UserProfileEntity up = UserProfileEntity.builder().supportsInlineQueries(SUPPORTS_INLINE_QUERIES).build();
        assertThat(up.getSupportsInlineQueries()).isEqualTo(SUPPORTS_INLINE_QUERIES);
    }

    @Test
    void shouldCheckSuppSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setSupportsInlineQueries(SUPPORTS_INLINE_QUERIES);
        assertThat(up.getSupportsInlineQueries()).isEqualTo(SUPPORTS_INLINE_QUERIES);
    }

    @Test
    void shouldCheckStateGetting() {
        UserProfileEntity up = UserProfileEntity.builder().state(STATE).build();
        assertThat(up.getState()).isEqualTo(STATE);
    }

    @Test
    void shouldCheckStateSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setState(STATE);
        assertThat(up.getState()).isEqualTo(STATE);
    }

    @Test
    void shouldCheckNotesGetting() {
        UserProfileEntity up = UserProfileEntity.builder().notes(NOTES).build();
        assertThat(up.getNotes()).isEqualTo(NOTES);
    }

    @Test
    void shouldCheckNotesSetting() {
        UserProfileEntity up = UserProfileEntity.builder().build();
        up.setNotes(NOTES);
        assertThat(up.getNotes()).isEqualTo(NOTES);
    }
}
