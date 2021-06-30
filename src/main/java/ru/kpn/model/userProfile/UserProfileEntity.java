package ru.kpn.model.userProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Document("users")
public class UserProfileEntity implements Serializable {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
    private Boolean isBot;
    private Boolean canJoinGroups;
    private Boolean canReadAllGroupMessages;
    private Boolean supportsInlineQueries;
    private Integer state;
    private Set<ObjectId> notes;
}
