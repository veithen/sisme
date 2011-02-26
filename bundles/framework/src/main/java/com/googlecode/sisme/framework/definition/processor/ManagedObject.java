package com.googlecode.sisme.framework.definition.processor;

import java.util.Dictionary;

import org.osgi.framework.ServiceRegistration;

class ManagedObject {
    private final Binder binder;
    private final String[] clazzes;
    private final ManagedObjectFactory factory;
    private final Dictionary properties;
    private ServiceRegistration registration;

    public ManagedObject(Binder binder, String[] clazzes, ManagedObjectFactory factory, Dictionary properties) {
        this.binder = binder;
        this.clazzes = clazzes;
        this.factory = factory;
        this.properties = properties;
    }

    void register() {
        Object service = factory.createObject();
        registration = binder.getBundleContext().registerService(clazzes, service, properties);
    }
    
    void unregister() {
        registration.unregister();
    }
}
