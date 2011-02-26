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
package com.googlecode.sisme.framework.definition.processor;

import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.googlecode.sisme.framework.Processor;
import com.googlecode.sisme.framework.definition.Definition;

public abstract class DefinitionProcessor implements Processor<Definition,DefinitionProcessorContext> {
    private final ServiceTracker tracker;
    
    public DefinitionProcessor(final BundleContext context, QName elementQName) {
        Filter filter;
        try {
            filter = context.createFilter("(&(objectClass=" + Definition.class.getName() + ")(" + Definition.P_ELEMENT_NAMESPACE + "="
                    + elementQName.getNamespaceURI() + ")(" + Definition.P_ELEMENT_NAME + "=" + elementQName.getLocalPart() + "))");
        } catch (InvalidSyntaxException ex) {
            throw new Error(ex);
        }
        tracker = new ServiceTracker(context, filter, new ServiceTrackerCustomizer() {
            public Object addingService(ServiceReference reference) {
                String namespace = (String)reference.getProperty(Definition.P_NAMESPACE);
                String name = (String)reference.getProperty(Definition.P_NAME);
                Definition definition = (Definition)context.getService(reference);
                // Managed objects are always registered into the same bundle context as the original definition
                Binder binder = new Binder(reference.getBundle().getBundleContext(), new QName(namespace, name));
                process(binder, definition);
                binder.start();
                return binder;
            }
            
            public void removedService(ServiceReference reference, Object service) {
                ((Binder)service).stop();
                context.ungetService(reference);
            }
            
            public void modifiedService(ServiceReference reference, Object service) {
            }
        });
    }
    
    public final void start() {
        tracker.open();
    }
    
    public final void stop() {
        tracker.close();
    }
    
    public abstract void process(DefinitionProcessorContext context, Definition definition);
}