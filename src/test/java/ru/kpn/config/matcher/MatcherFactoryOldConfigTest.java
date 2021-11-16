package ru.kpn.config.matcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.*;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MatcherFactoryOldConfigTest {

    @Autowired
    private MatcherFactoryOldImpl factory;

    @Test
    void shouldCheckConstantMatcherCreation() {
        Function<Update, Boolean> matcher = factory.create(MatcherType.CONSTANT, false);
        assertThat(ConstantMatcher.class).isEqualTo(matcher.getClass());
    }

    @Test
    void shouldCheckRegexMatcherCreation() {
        Function<Update, Boolean> matcher = factory.create(MatcherType.REGEX, "some regex");
        assertThat(RegexMatcher.class).isEqualTo(matcher.getClass());
    }

    @Test
    void shouldCheckRegexMatcherWrongCreation() {
        Function<Update, Boolean> matcher = factory.create(MatcherType.REGEX);
        assertThat(ConstantMatcher.class).isEqualTo(matcher.getClass());
    }

    @Test
    void shouldCheckMultiRegexMatcher() {
        Function<Update, Boolean> matcher = factory.create(MatcherType.MULTI_REGEX, "regex0", "regex1", "regex2");
        assertThat(MultiRegexMatcher.class).isEqualTo(matcher.getClass());
    }
}
