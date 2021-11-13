package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.model.userProfile.UserProfileEntity;
import ru.kpn.service.userProfile.UserProfileService;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.rawMessage.RawMessage;

import java.util.Optional;
import java.util.function.Function;

@Component
public class ResetStrategy extends BaseSubscriberStrategy {

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    @Autowired
    private UserProfileService service;

    @Inject(InjectionType.PRIORITY)
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Inject(InjectionType.MATCHER)
    public void setMatcher(Function<Update, Boolean> matcher){
        this.matcher = matcher;
    }

    @Override
    public RawMessage<String> runAndGetRawMessage(Update value) {
        resetState(value);
        String chatId = calculateChatId(value);
        return createRawMessage("strategy.message.reset")
                .add(chatId)
                .add(chatId);
    }

    private void resetState(Update value) {
        User user = getUser(value);
        NPBotState resetState = NPBotState.RESET;
        resetInService(user, resetState);
        resetInDB(user, resetState);
    }

    private void resetInService(User user, NPBotState state) {
        stateService.set(user, NPBotState.RESET);
    }

    private void resetInDB(User user, NPBotState state) {
        Optional<UserProfileEntity> maybeUser = service.getById(user.getId());
        UserProfileEntity userProfileEntity = maybeUser.orElseGet(() -> UserProfileEntity.create(user));
        userProfileEntity.setState(state.getId());
        service.save(userProfileEntity);
    }

    private User getUser(Update value) {
        return value.getMessage().getFrom();
    }
}
