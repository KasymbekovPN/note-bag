package ru.kpn.objectExtraction.creator.extractor;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kpn.rawMessage.BotRawMessageFactory;
import ru.kpn.rawMessage.RawMessageFactory;

public class ByPrefixesExtractorCreatorTest {

    private static final RawMessageFactory<String> MESSAGE_FACTORY = new BotRawMessageFactory();

    @Autowired
    private ByPrefixesExtractorCreator creator;
}
