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
package com.googlecode.sisme.framework.parser;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

class DependencyImpl<T> extends ServiceTracker implements Dependency<T> {
    private final Class<T> clazz;
    private final Binder binder;
    private final List<ServiceReference> candidates = new ArrayList<ServiceReference>();
    private ServiceReference boundService;
    private T boundObject;

    DependencyImpl(Class<T> clazz, Filter filter, Binder binder) {
        super(binder.getBundleContext(), filter, null);
        this.clazz = clazz;
        this.binder = binder;
    }
    
    boolean isSatisfied() {
        return candidates.size() == 1;
    }
    
    void bind() {
        boundService = candidates.get(0);
        boundObject = clazz.cast(binder.getBundleContext().getService(boundService));
    }
    
    void unbind() {
        binder.getBundleContext().ungetService(boundService);
        boundService = null;
        boundObject = null;
    }
    
    public T get() {
        if (boundObject == null) {
            throw new IllegalStateException("The dependency is not bound");
        } else {
            return boundObject;
        }
    }
    
    @Override
    public Object addingService(ServiceReference reference) {
        synchronized (binder) {
            candidates.add(reference);
            binder.rebind();
        }
        return null;
    }

    @Override
    public void removedService(ServiceReference reference, Object service) {
        synchronized (binder) {
            candidates.remove(reference);
            binder.rebind();
        }
    }
}
