package ru.kpn.strategy.injectors;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.matcher.RegexMatcher;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MatcherInjectorTest {

    @Autowired
    private MatcherInjector injector;

    @Test
    void shouldCheckAttemptOfInjectionWithoutInjectMethod() {
        String name = "help";
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.no.method").add(name).add(InjectionType.MATCHER);
        Result<Function<Update, Boolean>, RawMessage<String>> result = injector.inject(new TestWithoutInjectMethod(), name);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckAttemptOfInjectionWithoutInitData() {
        String name = "wrongName";
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.no.init-data").add(name).add(InjectionType.MATCHER);
        Result<Function<Update, Boolean>, RawMessage<String>> result = injector.inject(new TestWithInjectMethod(), name);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckInjection() {
        String name = "reset";
        TestWithInjectMethod object = new TestWithInjectMethod();
        Result<Function<Update, Boolean>, RawMessage<String>> result = injector.inject(object, name);
        assertThat(result.getSuccess()).isTrue();
        assertThat(object.getValue().getClass()).isEqualTo(RegexMatcher.class);
    }

    private static class TestWithoutInjectMethod{}

    @Getter
    private static class TestWithInjectMethod{
        private Function<Update, Boolean> value;

        @Inject(InjectionType.MATCHER)
        public void setValue(Function<Update, Boolean> value) {
            this.value = value;
        }
    }
}