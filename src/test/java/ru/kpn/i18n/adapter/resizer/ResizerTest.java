package ru.kpn.i18n.adapter.resizer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ResizerTest {

    private static Object[][] getResizeTestData(){
        return new Object[][]{
                {3,new Object[]{1,2,3,4,5},new Object[]{1,2,3}},
                {5,new Object[]{1,2,3,4,5},new Object[]{1,2,3,4,5}},
                {7,new Object[]{1,2,3,4,5},new Object[]{1,2,3,4,5,null,null}}
        };
    }

    @ParameterizedTest
    @MethodSource("getResizeTestData")
    public void shouldCheckResizing(Integer size, Object[] source, Object[] expectedResult){
        Resizer resizer = new Resizer(size);
        Object[] result = resizer.apply(source);
        assertThat(expectedResult).isEqualTo(result);
    }
}
