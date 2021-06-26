package ru.kpn.model.userProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.kpn.model.note.Note;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserProfile {
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
    private Boolean isBot;
    private Boolean canJoinGroups;
    private Boolean canReadAllGroupMessages;
    private Boolean supportsInlineQueries;
    private Integer state;
    private Set<Note> notes;
}
