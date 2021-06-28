package ru.kpn.model.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

// TODO: 28.06.2021 use SpEL for collection name
@Data
@Builder
@AllArgsConstructor
@Document("notes")
public class NoteEntity {
    @Id
    private ObjectId id;
    private Integer type;
    private ObjectId userId;
    private String name;
    private String content;
    private Set<ObjectId> tags;
}
