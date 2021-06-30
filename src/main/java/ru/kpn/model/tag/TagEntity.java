package ru.kpn.model.tag;

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
@Document("tags")
public class TagEntity implements Serializable {
    @Id
    private ObjectId id;
    private String name;
    private Set<ObjectId> notes;
}
