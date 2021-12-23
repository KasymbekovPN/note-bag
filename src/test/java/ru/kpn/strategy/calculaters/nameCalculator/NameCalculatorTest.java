package ru.kpn.strategy.calculaters.nameCalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.statusSeed.BotRawMessageOld;
import ru.kpn.statusSeed.RawMessageOld;

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
        Result<String, RawMessageOld<String>> result = calculator.apply(new SomeStrategy());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo("some");
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldCheckNameCalcOfWrongObjectWithoutSuffix(Object object, String code, Object[] args) {
        BotRawMessageOld expectedStates = new BotRawMessageOld(code);
        Arrays.stream(args).forEach(expectedStates::add);

        Result<String, RawMessageOld<String>> result = calculator.apply(object);
        assertThat(result.getSuccess()).isFalse();
        assertThat(expectedStates).isEqualTo(result.getStatus());
    }

    private static class SomeStrategy {}
    private static class SomeSSttrraatteeggyy {}
    private static class S {}
}