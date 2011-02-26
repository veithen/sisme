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
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.googlecode.sisme.framework.ManagedObjectFactory;

public class ManagedObjectFactoryTracker extends ServiceTracker {
    public ManagedObjectFactoryTracker(BundleContext context) {
        super(context, ManagedObjectFactory.class.getName(), null);
    }

    @Override
    public Object addingService(ServiceReference reference) {
        Dictionary<String,Object> props = new Hashtable<String,Object>();
        String clazz = null;
        for (String key : reference.getPropertyKeys()) {
            Object value = reference.getProperty(key);
            if (key.equals(ManagedObjectFactory.P_OBJECTCLASS)) {
                clazz = (String)value;
            } else {
                props.put(key, value);
            }
        }
        ManagedObjectImpl<?> managedObject = new ManagedObjectImpl((ManagedObjectFactory)context.getService(reference), reference.getBundle().getBundleContext(), clazz, props);
        managedObject.start();
        return managedObject;
    }

    @Override
    public void removedService(ServiceReference reference, Object service) {
        ((ManagedObjectImpl<?>)service).stop();
        context.ungetService(reference);
    }
}
