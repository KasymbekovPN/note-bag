package ru.kpn.strategy.calculaters.nameCalculator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NameCalculatorTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {new SomeSSttrraatteeggyy(), "calculation.name.fail", new Object[]{"SomeSSttrraatteeggyy", "Strategy"}},
                {new S(), "calculation.name.fail", new Object[]{"S", "Strategy"}}
        };
    }

    @Autowired
    private NameCalculator calculator;

    @Test
    void shouldCheckNameCalculation() {
        Result<String, RawMessage<String>> result = calculator.calculate(new SomeStrategy());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo("some");
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldCheckNameCalcOfWrongObjectWithoutSuffix(Object object, String code, Object[] args) {
        BotRawMessage expectedStates = new BotRawMessage(code);
        Arrays.stream(args).forEach(expectedStates::add);

        Result<String, RawMessage<String>> result = calculator.calculate(object);
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStates).isEqualTo(result.getStatus());
    }

    private static class SomeStrategy {}
    private static class SomeSSttrraatteeggyy {}
    private static class S {}
}