package ru.kpn.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration()
public class CollectionNamesConfig {
    @Value("${mongo.collectionNames.note}")
    private String noteCollectionName;
    @Value("${mongo.collectionNames.tag}")
    private String tagCollectionName;
    @Value("${mongo.collectionNames.user}")
    private String userCollectionName;
}
