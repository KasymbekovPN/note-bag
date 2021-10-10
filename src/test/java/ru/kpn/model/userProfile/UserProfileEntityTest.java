package ru.kpn.model.userProfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.NPBotState;
import utils.TestDataFormer;

import static org.assertj.core.api.Assertions.assertThat;

public class UserProfileEntityTest {

    private static final Long ID = 123L;
    private static final Long WRONG_ID = 124L;

    private static final String FIRST_NAME = "First name";
    private static final String WRONG_FIRST_NAME = "Wrong first name";

    private static final String LAST_NAME = "Last name";
    private static final String WRONG_LAST_NAME = "Wrong last name";

    private static final String USER_NAME = "User name";
    private static final String WRONG_USER_NAME = "Wrong user name";

    private static final String LANGUAGE_CODE = "Lang code";
    private static final String WRONG_LANGUAGE_CODE = "Wrong lang code";

    private static final int STATE = 0;
    private static final int WRONG_STATE = 1;

    private static Object[][] getInitData(){
        return new TestDataFormer()
                .append(ID, WRONG_ID)
                .append(FIRST_NAME, WRONG_FIRST_NAME)
                .append(LAST_NAME, WRONG_LAST_NAME)
                .append(USER_NAME, WRONG_USER_NAME)
                .append(LANGUAGE_CODE, WRONG_LANGUAGE_CODE)
                .append(STATE, WRONG_STATE)
                .form();
    }

    private UserProfileEntity bUserProfileEntity;
    private UserProfileEntity cUserProfileEntity;

    @BeforeEach
    void setUp() {
        cUserProfileEntity = new UserProfileEntity(
                ID,
                FIRST_NAME,
                LAST_NAME,
                USER_NAME,
                LANGUAGE_CODE,
                STATE
        );
        bUserProfileEntity = UserProfileEntity.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .userName(USER_NAME)
                .languageCode(LANGUAGE_CODE)
                .state(STATE)
                .build();
    }

    @Test
    void shouldCompareBAndC() {
        assertThat(bUserProfileEntity).isEqualTo(cUserProfileEntity);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareBAndB(Long id,
                            String firstName,
                            String lastName,
                            String userName,
                            String langCode,
                            Integer state,
                            Boolean expectedResult){
        UserProfileEntity up = UserProfileEntity.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .languageCode(langCode)
                .state(state)
                .build();
        assertThat(up.equals(bUserProfileEntity)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getInitData")
    void shouldCompareCAndC(Long id,
                            String firstName,
                            String lastName,
                            String userName,
                            String langCode,
                            Integer state,
                            Boolean expectedResult){
        UserProfileEntity up = new UserProfileEntity(
                id,
                firstName,
                lastName,
                userName,
                langCode,
                state
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
    void shouldTestCreateMethod() {
        UserProfileEntity expectedUserProfileEntity = UserProfileEntity.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .userName(USER_NAME)
                .languageCode(LANGUAGE_CODE)
                .state(NPBotState.UNKNOWN.getId())
                .build();
        User user = new User();
        user.setId(ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setUserName(USER_NAME);
        user.setLanguageCode(LANGUAGE_CODE);

        UserProfileEntity userProfileEntity = UserProfileEntity.create(user);
        assertThat(expectedUserProfileEntity).isEqualTo(userProfileEntity);
    }
}
