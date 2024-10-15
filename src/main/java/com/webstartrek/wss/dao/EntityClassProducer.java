package com.webstartrek.wss.dao;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.reflect.ParameterizedType;

public class EntityClassProducer {

    @Produces
    public <T> Class<T> produceEntityClass(InjectionPoint injectionPoint) {
        // Obtain the type of the injection point and return the Class type of the entity
        return (Class<T>) ((ParameterizedType) injectionPoint.getType()).getActualTypeArguments()[0];
    }
}
