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
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.w3c.dom.Element;

/**
 * Tracks a set of dependencies and automatically registers a service when each
 * dependency is satisfied exactly once.
 * 
 * @author Andreas Veithen
 */
class Binder implements DefinitionParserContext {
    private final BundleContext bundleContext;
    private final List<DependencyImpl<?>> dependencies = new ArrayList<DependencyImpl<?>>();
    private final List<ManagedObject> managedObjects = new ArrayList<ManagedObject>();
    private boolean started;

    Binder(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    BundleContext getBundleContext() {
        return bundleContext;
    }
    
    synchronized void rebind() {
    	boolean satisfied = true;
    	for (DependencyImpl<?> dependency : dependencies) {
    	    if (!dependency.isSatisfied()) {
    	        satisfied = false;
    	        break;
    	    }
    	}
    	if (!started && satisfied) {
    	    
    	    for (DependencyImpl<?> dependency : dependencies) {
    	        dependency.bind();
    	    }
    	    
    	} else if (started && !satisfied) {
    	    
            for (DependencyImpl<?> dependency : dependencies) {
                dependency.unbind();
            }
    	    
    	    
    	}
    }
    
    void start() {
        
    }
    
    void stop() {
        
    }

    public <T> Dependency<T> createDependency(Class<T> clazz, Element element) {
        // TODO Auto-generated method stub
        return null;
    }

    public void addManagedObject(String clazz, Object object) {
        addManagedObjectFactory(clazz, new ManagedObjectWrapper(object));
    }

    public void addManagedObjectFactory(String clazz, ManagedObjectFactory factory) {
        Properties properties = new Properties();
        
        managedObjects.add(new ManagedObject(this, new String[] { clazz }, factory, properties));
    }

}
