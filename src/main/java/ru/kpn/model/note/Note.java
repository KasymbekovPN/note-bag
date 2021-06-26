package ru.kpn.model.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.kpn.model.tag.Tag;
import ru.kpn.model.userProfile.UserProfile;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class Note {
    private int type;
    private String name;
    private String content;
    private UserProfile userProfile;
    private Set<Tag> tags;
}
