package ru.kpn.service.env;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "property0 = 0",
        "property1 = 1",
        "property2 = 2"
})
public class EnvironmentServiceImplTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        new HashMap<>(){{
                            put("property0", null);
                            put("property1", null);
                        }},
                        new HashMap<>(){{
                            put("property0", 0);
                            put("property1", 1);
                        }},
                        true
                },
                {
                        new HashMap<>(){{
                            put("property0", null);
                            put("property1", null);
                            put("property2", null);
                        }},
                        new HashMap<>(){{
                            put("property0", 0);
                            put("property1", 1);
                            put("property2", 2);
                        }},
                        true
                },
                {
                        new HashMap<>(){{
                            put("property0", null);
                            put("property1", null);
                            put("property2", null);
                            put("property3", null);
                        }},
                        new HashMap<>(){{
                            put("property0", 0);
                            put("property1", 1);
                            put("property2", 2);
                            put("property3", null);
                        }},
                        false
                }
        };
    }

    @Autowired
    private EnvironmentService environmentService;

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldFillTestPropertyChunk(Map<String, Integer> props, Map<String, Integer> checkProps, Boolean check) {
        TestPropertyChunk testPropertyChunk = new TestPropertyChunk(props);
        assertThat(environmentService.fillChunk(testPropertyChunk)).isEqualTo(check);
        assertThat(testPropertyChunk.props).isEqualTo(checkProps);
    }

    private static class TestPropertyChunk implements PropertyChunk{

        private final Map<String, Integer> props;

        public TestPropertyChunk(Map<String, Integer> props) {
            this.props = props;
        }

        @Override
        public Boolean fillSelf(Environment environment) {
            boolean result = true;

            for (String key : props.keySet()) {
                if (environment.containsProperty(key)){
                    props.put(key, Integer.valueOf(Objects.requireNonNull(environment.getProperty(key))));
                } else {
                    result = false;
                }
            }
            return result;
        }
    }
}
