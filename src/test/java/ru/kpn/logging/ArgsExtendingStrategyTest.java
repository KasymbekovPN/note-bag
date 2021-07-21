package ru.kpn.logging;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsExtendingStrategyTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {new Object[]{"Test", " : ", 1}}
        }; 
    }
    
    private final static CustomizableLogger.LogLevel LOG_LEVEL = CustomizableLogger.LogLevel.TRACE;

    private final ExtendingStrategy<Object[]> strategy = new ArgsExtendingStrategy();

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldExtentArgs(Object[] args) {
        Object[] extendedArgs = strategy.execute(args, LOG_LEVEL, this.getClass());

        assertThat(extendedArgs.length).isEqualTo(args.length + 5);
        assertThat(extendedArgs[1]).isEqualTo(LOG_LEVEL);
        assertThat(extendedArgs[4]).isEqualTo(this.getClass().getSimpleName());
        for (int i = 0; i < args.length; i++) {
            assertThat(args[i]).isEqualTo(extendedArgs[5 + i]);
        }
    }
}
