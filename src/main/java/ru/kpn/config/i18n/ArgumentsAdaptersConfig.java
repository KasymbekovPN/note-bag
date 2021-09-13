package ru.kpn.config.i18n;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpn.i18n.adapter.argument.SimpleNameClassArgumentAdapter;
import ru.kpn.i18n.adapter.argument.SimpleNameInstanceArgumentAdapter;
import ru.kpn.i18n.adapter.arguments.ArgumentsAdapter;
import ru.kpn.i18n.adapter.arguments.ArgumentsAdapterImpl;
import ru.kpn.i18n.adapter.resizer.Resizer;

@Configuration
public class ArgumentsAdaptersConfig {

    @Qualifier("factorySubscriberBuilding")
    @Bean
    public ArgumentsAdapter factorySubscriberBuilding(){
        return ArgumentsAdapterImpl.builder()
                .code("factory.subscriberBuilding")
                .resizer(new Resizer(3))
                .adapter(0, new SimpleNameClassArgumentAdapter())
                .adapter(1, new SimpleNameInstanceArgumentAdapter())
                .adapter(2, new SimpleNameInstanceArgumentAdapter())
                .build();
    }
}
