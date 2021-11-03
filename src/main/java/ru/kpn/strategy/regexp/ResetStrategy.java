package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.BotStateService;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.model.userProfile.UserProfileEntity;
import ru.kpn.service.userProfile.UserProfileService;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.Optional;

@Component
public class ResetStrategy extends BaseSubscriberStrategy {

    @Autowired
    private BotStateService<User, NPBotState> stateService;

    @Autowired
    private UserProfileService service;

    @Value("${telegram.tube.strategies.reset.priority}")
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        resetState(value);
        String chatId = calculateChatId(value);
        StrategyCalculatorSource<String> source = createSource("strategy.message.reset");
        source.add(chatId);
        source.add(chatId);

        return source;
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
