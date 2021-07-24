package ru.kpn.service.env;

import org.springframework.core.env.Environment;

public interface PropertyChunk {
    Boolean fillSelf(Environment environment);
}
