package com.googlecode.sisme.framework.impl;

import java.util.Dictionary;

import org.osgi.framework.ServiceRegistration;

import com.googlecode.sisme.framework.ObjectFactory;

public class Service {
    private final AbstractProcessorContext context;
    private final String[] clazzes;
    private final ObjectFactory factory;
    private final Dictionary<String,Object> properties;
    private ServiceRegistration registration;

    public Service(AbstractProcessorContext context, String[] clazzes, ObjectFactory factory, Dictionary<String,Object> properties) {
        this.context = context;
        this.clazzes = clazzes;
        this.factory = factory;
        this.properties = properties;
    }

    public void register() {
        Object service = factory.createObject();
        registration = context.getBundleContext().registerService(clazzes, service, properties);
    }
    
    public void unregister() {
        registration.unregister();
    }
}
