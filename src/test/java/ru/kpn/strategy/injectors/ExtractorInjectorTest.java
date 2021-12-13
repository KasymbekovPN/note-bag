package ru.kpn.strategy.injectors;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExtractorInjectorTest {

    @Autowired
    private ExtractorInjector injector;

    @Test
    void shouldCheckAttemptOfInjectionWithoutInjectMethod() {
        String name = "simpleNote";
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.no.method").add(name).add(InjectionType.EXTRACTOR);
        Result<Function<Update, String>, RawMessage<String>> result = injector.inject(new TestWithoutInjectMethod(), name);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckAttemptOfInjectionWithoutInitData() {
        String name = "wrongName";
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.no.init-data").add(name).add(InjectionType.EXTRACTOR);
        Result<Function<Update, String>, RawMessage<String>> result = injector.inject(new TestWithInjectMethod(), name);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckInjection() {
        String name = "simpleNote";
        TestWithInjectMethod object = new TestWithInjectMethod();
        Result<Function<Update, String>, RawMessage<String>> result = injector.inject(object, name);
        assertThat(result.getSuccess()).isTrue();
        assertThat(object.getValue().getClass()).isEqualTo(ByPrefixExtractor.class);
    }

    private static class TestWithoutInjectMethod{}

    @Getter
    private static class TestWithInjectMethod{
        private Function<Update, String> value;

        @Inject(InjectionType.EXTRACTOR)
        public void setValue(Function<Update, String> value) {
            this.value = value;
        }
    }
}