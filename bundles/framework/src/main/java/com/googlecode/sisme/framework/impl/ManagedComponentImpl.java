/*
 * Copyright 2011 Andreas Veithen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.sisme.framework.impl;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.googlecode.sisme.framework.component.ManagedComponent;
import com.googlecode.sisme.framework.component.ManagedComponentFactory;

public class ManagedComponentImpl<T> implements ManagedComponent {
    private final ManagedComponentFactory<T> factory;
    private final BundleContext targetContext;
    private final String clazz;
    private final Dictionary<String,Object> properties;
    private int state = STOPPED;
    private T instance;
    private ServiceRegistration registration;

    public ManagedComponentImpl(ManagedComponentFactory<T> factory, BundleContext targetContext, String clazz, Dictionary<String,Object> properties) {
        this.factory = factory;
        this.targetContext = targetContext;
        this.clazz = clazz;
        this.properties = properties;
    }
    
    public void start() {
        state = STARTING;
        instance = factory.create();
        registration = targetContext.registerService(clazz, instance, properties);
        state = STARTED;
    }
    
    public void stop() {
        state = STOPPING;
        registration.unregister();
        factory.destroy(instance);
        state = STOPPED;
    }
    
    public int getState() {
        return state;
    }
}
