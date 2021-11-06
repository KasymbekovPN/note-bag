package ru.kpn.creator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.extractor.ExtractorFactory;
import ru.kpn.extractor.ExtractorType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyExtractorCreatorTest {

    private final Map<String, StrategyExtractorCreator.Datum> extractorInitData = new HashMap<>();

    private StrategyExtractorCreator creator;

    @BeforeEach
    void setUp() {
        creator = new StrategyExtractorCreator();
        creator.setFactory(new TestExtractorFactory());

        StrategyExtractorCreator.Datum withoutTypeDatum = new StrategyExtractorCreator.Datum();
        extractorInitData.put("withoutTypeDatum", withoutTypeDatum);

        StrategyExtractorCreator.Datum wrongTypeDatum = new StrategyExtractorCreator.Datum();
        wrongTypeDatum.setType("WRONG");
        extractorInitData.put("wrongTypeDatum", wrongTypeDatum);

        StrategyExtractorCreator.Datum wrongArgsByPrefixDatum = new StrategyExtractorCreator.Datum();
        wrongArgsByPrefixDatum.setType(ExtractorType.BY_PREFIX.name());
        extractorInitData.put("wrongArgsByPrefixDatum", wrongArgsByPrefixDatum);

        StrategyExtractorCreator.Datum byPrefixDatum = new StrategyExtractorCreator.Datum();
        byPrefixDatum.setType(ExtractorType.BY_PREFIX.name());
        byPrefixDatum.setPrefixes(Set.of("prefix0", "prefix1"));
        extractorInitData.put("byPrefixDatum", byPrefixDatum);

        creator.setExtractorInitData(extractorInitData);
    }

    @Test
    void shouldCheckWithoutTypeCreation() {
        StrategyExtractorCreator.Result result = creator.getOrCreate("withoutTypeDatum");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Type for 'withoutTypeDatum' is null");
    }

    @Test
    void shouldCheckWrongTypeDatumCreation() {
        StrategyExtractorCreator.Result result = creator.getOrCreate("wrongTypeDatum");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Type 'WRONG' is invalid [wrongTypeDatum]");
    }

    @Test
    void shouldCheckWrongArgsByPrefixDatum() {
        StrategyExtractorCreator.Result result = creator.getOrCreate("wrongArgsByPrefixDatum");
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Invalid args for [wrongArgsByPrefixDatum]");
    }

    @Test
    void shouldByPrefixDatum() {
        StrategyExtractorCreator.Result result = creator.getOrCreate("byPrefixDatum");
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getExtractor().getClass()).isEqualTo(ByPrefixExtractor.class);
    }

    private static class TestExtractorFactory implements ExtractorFactory<Update, String>{
        @Override
        public Function<Update, String> create(ExtractorType type, Object... args) {
            if (type.equals(ExtractorType.BY_PREFIX)){
                Set<String> prefixes = Arrays.stream(args).map(String::valueOf).collect(Collectors.toSet());
                return new ByPrefixExtractor(prefixes);
            }
            return null;
        }
    }
}
