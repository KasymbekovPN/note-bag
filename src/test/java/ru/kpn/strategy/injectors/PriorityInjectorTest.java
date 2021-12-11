package ru.kpn.strategy.injectors;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 11.12.2021 it must be BaseInjectorTest 
@SpringBootTest
class PriorityInjectorTest {

    @Autowired
    private PriorityInjector injector;

    @Test
    void shouldCheckAttemptOfInjectionWithoutInjectMethod() {
        String name = "help";
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.no.method").add(name).add(InjectionType.PRIORITY);
        Result<Integer, RawMessage<String>> result = injector.inject(new TestWithoutInjectMethod(), name);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckAttemptOfInjectionWithoutInitData() {
        String name = "wrongName";
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.no.init-data").add(name).add(InjectionType.PRIORITY);
        Result<Integer, RawMessage<String>> result = injector.inject(new TestWithInjectMethod(), name);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckInjection() {
        String name = "reset";
        int priority = 2;
        TestWithInjectMethod object = new TestWithInjectMethod();
        Result<Integer, RawMessage<String>> result = injector.inject(object, name);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(priority);
        assertThat(object.getValue()).isEqualTo(priority);
    }

    private static class TestWithoutInjectMethod{}

    @Getter
    private static class TestWithInjectMethod{
        private int value;

        @Inject(InjectionType.PRIORITY)
        public void setValue(int value) {
            this.value = value;
        }
    }
}