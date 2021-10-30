package ru.kpn.extractor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utils.UpdateInstanceBuilder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ByPrefixExtractorTest {

    private static final Set<String> PREFIXES = Set.of("/sn ", "/simple note ");

    private ByPrefixExtractor extractor;

    @BeforeEach
    void setUp() {
        extractor = new ByPrefixExtractor(PREFIXES);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckExtraction_simpleNote.csv")
    void shouldCheckExtraction(String command, String expectedExtraction, Boolean expectedResult) {
        String extraction = extractor.apply(new UpdateInstanceBuilder().text(command).build());
        assertThat(expectedExtraction.equals(extraction)).isEqualTo(expectedResult);
    }
}
