package ru.kpn.model.userProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.bot.state.NPBotState;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Document("users")
public class UserProfileEntity implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
    private int state;
    
    public static UserProfileEntity create(User user) {
        return builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .languageCode(user.getLanguageCode())
                .state(NPBotState.UNKNOWN.getId())
                .build();
    }
}
