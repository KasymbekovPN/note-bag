package ru.kpn.strategy.injectors;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;
import ru.kpn.strategy.strategies.BaseSubscriberStrategy;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PriorityInjectorTest {

    private static final InjectionType TYPE = InjectionType.PRIORITY;
    @Autowired
    private PriorityInjector injector;

    @Test
    void shouldCheckNameCalculation() {
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.name.wrong").add(TYPE).add(StrategyWithoutSuffix.class.getSimpleName());
        Result<Integer, RawMessage<String>> result = injector.inject(new StrategyWithoutSuffix());
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckAttemptOfInjectionWithoutInjectMethod() {
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.no.method").add("testWithoutInjectMethod").add(TYPE);
        Result<Integer, RawMessage<String>> result = injector.inject(new TestWithoutInjectMethodStrategy());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckAttemptOfInjectionWithoutInitData() {
        RawMessage<String> expectedStatus
                = new BotRawMessage("injection.no.init-data").add("wrongNameTest").add(TYPE);
        Result<Integer, RawMessage<String>> result = injector.inject(new WrongNameTestStrategy());
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }
    @Test
    void shouldCheckInjection() {
        int priority = 2;
        ResetStrategy object = new ResetStrategy();
        Result<Integer, RawMessage<String>> result = injector.inject(object);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(priority);
        assertThat(object.getValue()).isEqualTo(priority);
    }

    private static class StrategyWithoutSuffix extends BaseSubscriberStrategy {
        @Override
        public RawMessage<String> runAndGetRawMessage(Update value) {
            return null;
        }
    }

    private static class TestWithoutInjectMethodStrategy extends BaseSubscriberStrategy{
        @Override
        public RawMessage<String> runAndGetRawMessage(Update value) {
            return null;
        }
    }

    private static class WrongNameTestStrategy extends BaseSubscriberStrategy{
        @Override
        public RawMessage<String> runAndGetRawMessage(Update value) {
            return null;
        }

        @Inject(InjectionType.PRIORITY)
        public void setValue(Function<Update, String> value) {}
    }

    @Getter
    private static class ResetStrategy extends BaseSubscriberStrategy{
        private int value;

        @Override
        public RawMessage<String> runAndGetRawMessage(Update value) {
            return null;
        }

        @Inject(InjectionType.PRIORITY)
        public void setValue(int value) {
            this.value = value;
        }
    }
}