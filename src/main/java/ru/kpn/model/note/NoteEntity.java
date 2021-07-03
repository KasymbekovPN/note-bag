package ru.kpn.model.note;

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
@Document("notes")
public class NoteEntity implements Serializable {
    @Id
    private ObjectId id;
    private Integer type;
    private Integer userId;
    private String name;
    private String content;
    private Set<ObjectId> tags;
}
