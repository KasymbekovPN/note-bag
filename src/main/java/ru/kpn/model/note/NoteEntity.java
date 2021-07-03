package ru.kpn.model.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

// TODO: 28.06.2021 use SpEL for collection name
@Data
@Builder
@AllArgsConstructor
//@Document("notes")
//@Document("#{@collectionNamesConfig.getNoteCollectionName()}")
@Document("#{collectionNamesConfig.getNoteCollectionName()}")
//<
//    @Document(collection = "#{@mySpecialProvider.getTargetCollectionName()}")
//@Document("#{$mongo.collectionNames.note}")
//@Document("${mongo.collectionNames.note}")
public class NoteEntity implements Serializable {
    @Id
    private ObjectId id;
    private Integer type;
    private ObjectId userId;
    private String name;
    private String content;
    private Set<ObjectId> tags;
}
