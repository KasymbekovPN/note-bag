package ru.kpn.service.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    @Autowired
    private Environment environment;

    @Override
    public Boolean fillChunk(PropertyChunk propertyChunk) {
        return propertyChunk.fillSelf(environment);
    }
}
