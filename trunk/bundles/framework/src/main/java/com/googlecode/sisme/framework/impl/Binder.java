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
import java.util.Properties;

import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.w3c.dom.Element;

import com.googlecode.sisme.framework.definition.Definition;
import com.googlecode.sisme.framework.definition.processor.DefinitionProcessorContext;
import com.googlecode.sisme.framework.definition.processor.Dependency;

/**
 * Tracks a set of dependencies and automatically registers a service when every
 * dependency is satisfied exactly once.
 * 
 * @author Andreas Veithen
 */
class Binder extends AbstractProcessorContext implements DefinitionProcessorContext {
    private final QName objectName;
    private final Long componentId;
    private final List<ServiceRegistration> registeredDefinitions = new ArrayList<ServiceRegistration>();
    private final List<DependencyImpl<?>> dependencies = new ArrayList<DependencyImpl<?>>();
    private boolean started;

    Binder(BundleContext bundleContext, QName objectName, Long componentId) {
        super(bundleContext);
        this.objectName = objectName;
        this.componentId = componentId;
    }

    void rebind() {
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
    	    registerServices();
    	    started = true;
    	} else if (started && !satisfied) {
    	    unregisterServices();
            for (DependencyImpl<?> dependency : dependencies) {
                dependency.unbind();
            }
            started = false;
    	}
    }
    
    public void start() {
        if (dependencies.isEmpty()) {
            registerServices();
        } else {
            for (DependencyImpl<?> dependency : dependencies) {
                dependency.open();
            }
        }
    }
    
    public void stop() {
        if (dependencies.isEmpty()) {
            unregisterServices();
        } else {
            for (DependencyImpl<?> dependency : dependencies) {
                dependency.close();
            }
        }
        for (ServiceRegistration registration : registeredDefinitions) {
            registration.unregister();
        }
    }

    public <T> Dependency<T> createDependency(Class<T> clazz, QName ref, Element content) {
        // TODO: need to report an error if both ref and content are given
        if (ref == null) {
            // Generate a new unique name
            ref = new QName(objectName.getNamespaceURI(), objectName.getLocalPart() + "$" + dependencies.size());
            // Register a Definition object
            Properties props = new Properties();
            props.setProperty(Definition.P_ELEMENT_NAMESPACE, content.getNamespaceURI());
            props.setProperty(Definition.P_ELEMENT_NAME, content.getLocalName());
            props.setProperty(Definition.P_NAMESPACE, ref.getNamespaceURI());
            props.setProperty(Definition.P_NAME, ref.getLocalPart());
            registeredDefinitions.add(bundleContext.registerService(Definition.class.getName(), new Definition(content), props));
        }
        Filter filter;
        try {
            filter = bundleContext.createFilter("(&(objectClass=" + clazz.getName() + ")(namespace="
                    + ref.getNamespaceURI() + ")(name=" + ref.getLocalPart() + "))");
        } catch (InvalidSyntaxException ex) {
            throw new Error(ex);
        }
        DependencyImpl<T> dependency = new DependencyImpl<T>(clazz, filter, this);
        dependencies.add(dependency);
        return dependency;
    }

    @Override
    protected void processProperties(Dictionary<String, Object> properties) {
        properties.put("namespace", objectName.getNamespaceURI());
        properties.put("name", objectName.getLocalPart());
        // TODO: use constant here
        properties.put("component.id", componentId);
    }
}
