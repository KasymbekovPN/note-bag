package ru.kpn.strategy.calculaters.nameCalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.seed.Seed;
import ru.kpn.seed.SeedBuilder;
import ru.kpn.seed.StringSeedBuilderFactoryOld;

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
        Result<String, Seed<String>> result = calculator.apply(new SomeStrategy());
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo("some");
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldCheckNameCalcOfWrongObjectWithoutSuffix(Object object, String code, Object[] args) {
        SeedBuilder<String> builder = StringSeedBuilderFactoryOld.builder().code(code);
        Arrays.stream(args).forEach(builder::arg);

        Result<String, Seed<String>> result = calculator.apply(object);
        assertThat(result.getSuccess()).isFalse();
        assertThat(builder.build()).isEqualTo(result.getStatus());
    }

    private static class SomeStrategy {}
    private static class SomeSSttrraatteeggyy {}
    private static class S {}
}