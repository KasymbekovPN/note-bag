package ru.kpn.model.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Note {
    private int type;
    private String name;
    private String content;
}
