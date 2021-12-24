package ru.kpn.strategy.injectors;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactory;
import ru.kpn.strategy.strategies.BaseSubscriberStrategy;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExtractorInjectorTest {

    private static final InjectionType TYPE = InjectionType.EXTRACTOR;

    @Autowired
    private ExtractorInjector injector;

    @Test
    void shouldCheckNameCalculation() {
        Seed<String> expectedStatus = StringSeedBuilderFactory.builder().code("injection.name.wrong").arg(TYPE).arg(StrategyWithoutSuffix.class.getSimpleName()).build();
        Result<Function<Update, String>, Seed<String>> result = injector.inject(new StrategyWithoutSuffix());
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckAttemptOfInjectionWithoutInjectMethod() {
        Seed<String> expectedStatus = StringSeedBuilderFactory.builder().code("injection.no.method").arg("testWithoutInjectMethod").arg(TYPE).build();
        Result<Function<Update, String>, Seed<String>> result = injector.inject(new TestWithoutInjectMethodStrategy());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckAttemptOfInjectionWithoutInitData() {
        Seed<String> expectedStatus = StringSeedBuilderFactory.builder().code("injection.no.init-data").arg("wrongNameTest").arg(TYPE).build();
        Result<Function<Update, String>, Seed<String>> result = injector.inject(new WrongNameTestStrategy());
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldCheckInjection() {
        SimpleNoteStrategy object = new SimpleNoteStrategy();
        Result<Function<Update, String>, Seed<String>> result = injector.inject(object);
        assertThat(result.getSuccess()).isTrue();
        assertThat(object.getValue().getClass()).isEqualTo(ByPrefixExtractor.class);
    }

    private static class StrategyWithoutSuffix extends BaseSubscriberStrategy {
        @Override
        public Seed<String> runAndGetRawMessage(Update value) {
            return null;
        }
    }

    private static class TestWithoutInjectMethodStrategy extends BaseSubscriberStrategy{
        @Override
        public Seed<String> runAndGetRawMessage(Update value) {
            return null;
        }
    }

    private static class WrongNameTestStrategy extends BaseSubscriberStrategy{
        @Override
        public Seed<String> runAndGetRawMessage(Update value) {
            return null;
        }

        @Inject(InjectionType.EXTRACTOR)
        public void setValue(Function<Update, String> value) {}
    }

    @Getter
    private static class SimpleNoteStrategy extends BaseSubscriberStrategy{
        private Function<Update, String> value;

        @Override
        public Seed<String> runAndGetRawMessage(Update value) {
            return null;
        }

        @Inject(InjectionType.EXTRACTOR)
        public void setValue(Function<Update, String> value) {
            this.value = value;
        }
    }
}