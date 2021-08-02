package ru.kpn.config.mongo;

import com.mongodb.ConnectionString;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import ru.kpn.decryptor.Decryptor;

@Setter
@Configuration
@ConfigurationProperties(prefix = "mongo")
public class MongoConfig {

    @Autowired
    private Decryptor decryptor;

    private String uriTemplate;
    private MongoTemplateData mongoTemplateData;

    @Bean
    public MongoTemplate mongoTemplate() {
        String uri = calculateMongoClientUri();
        ConnectionString connectionString = new ConnectionString(uri);
        SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(connectionString);

        return new MongoTemplate(factory);
    }

    private String calculateMongoClientUri() {
        String login = decryptor.decrypt(mongoTemplateData.getLogin());
        String password = decryptor.decrypt(mongoTemplateData.getPassword());
        String svrHost = decryptor.decrypt(mongoTemplateData.getSrvHost());
        String dbName = decryptor.decrypt(mongoTemplateData.getDbName());
        String uriParams = decryptor.decrypt(mongoTemplateData.getUriParams());

        return String.format(uriTemplate, login, password, svrHost, dbName, uriParams);
    }

    @Setter
    @Getter
    private static class MongoTemplateData{
        private String login;
        private String password;
        private String srvHost;
        private String dbName;
        private String uriParams;
    }
}
