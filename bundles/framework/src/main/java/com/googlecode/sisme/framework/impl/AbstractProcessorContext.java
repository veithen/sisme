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

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.BundleContext;

import com.googlecode.sisme.framework.ObjectFactory;
import com.googlecode.sisme.framework.ProcessorContext;

public abstract class AbstractProcessorContext implements ProcessorContext {
    protected final BundleContext bundleContext;
    private final List<Service> services = new ArrayList<Service>();

    public AbstractProcessorContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public final BundleContext getBundleContext() {
        return bundleContext;
    }

    public final void addService(String clazz, Object service) {
        addService(clazz, new InstanceWrapper(service));
    }
    
    public final void addService(String clazz, Object service, Dictionary<String,Object> properties) {
        addService(clazz, new InstanceWrapper(service));
    }
    
    public final void addService(String clazz, ObjectFactory serviceFactory, Dictionary<String,Object> properties) {
        services.add(new Service(this, new String[] { clazz }, serviceFactory, properties));
    }
    
    // TODO: should be private or disappear
    protected void addService(Service service) {
        services.add(service);
    }

    protected final void registerServices() {
        for (Service service : services) {
            service.register();
        }
    }
    
    protected void unregisterServices() {
        for (Service service : services) {
            service.unregister();
        }
    }
}
