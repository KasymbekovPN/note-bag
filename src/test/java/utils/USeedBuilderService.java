package utils;

import ru.kpn.seed.SeedBuilder;
import ru.kpn.seed.StringSeedBuilderService;

public class USeedBuilderService {

    private static final StringSeedBuilderService SERVICE = new StringSeedBuilderService();

    static public SeedBuilder<String> takeNew(){
        return SERVICE.takeNew();
    }
}
