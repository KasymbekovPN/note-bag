package ru.kpn.model.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.kpn.model.note.Note;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class Tag {
    private String name;
    private Set<Note> notes;
}
