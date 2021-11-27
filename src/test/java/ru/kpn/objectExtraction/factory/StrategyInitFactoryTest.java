// TODO: 27.11.2021 del
//package ru.kpn.objectExtraction.factory;
//
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.RepeatedTest;
//
//import java.util.Random;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class StrategyInitFactoryTest {
//
//    private static final Random RANDOM = new Random();
//
//    private ObjectFactory<StrategyInitFactory.Type, Integer> factory;
//
//    @SneakyThrows
//    @BeforeEach
//    void setUp() {
//        factory = StrategyInitFactory.builder().build();
//    }
//
//    @RepeatedTest(100)
//    void shouldCheckCreation() {
//        int expectedValue = RANDOM.nextInt();
//        Integer value = factory.create(StrategyInitFactory.Type.COMMON, expectedValue);
//        assertThat(expectedValue).isEqualTo(value);
//    }
//}
