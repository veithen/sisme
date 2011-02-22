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

import java.util.Dictionary;

import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.w3c.dom.Element;

import com.googlecode.sisme.framework.Definition;

public abstract class DefinitionParser {
    private static class Registrations implements DefinitionParserContext {
        
        public void registerService(String clazz, Object service, Dictionary properties) {
            // TODO Auto-generated method stub
            
        }

        public void unregister() {
            
        }

        public void addManagedObject(String clazz, Object object) {
            // TODO Auto-generated method stub
            
        }

        public void addManagedObjectFactory(String clazz,
                ManagedObjectFactory factory) {
            // TODO Auto-generated method stub
            
        }

        public <T> Dependency<T> createDependency(Class<T> clazz,
                Element element) {
            // TODO Auto-generated method stub
            return null;
        }
    }
    
    private final ServiceTracker tracker;
    
    public DefinitionParser(final BundleContext context, QName elementQName) {
        Filter filter;
        try {
            filter = context.createFilter("(&(objectClass=" + Definition.class.getName() + ")(" + Definition.P_ELEMENT_NAMESPACE + "="
                    + elementQName.getNamespaceURI() + ")(" + Definition.P_ELEMENT_NAME + "=" + elementQName.getLocalPart() + "))");
        } catch (InvalidSyntaxException ex) {
            throw new Error(ex);
        }
        tracker = new ServiceTracker(context, filter, new ServiceTrackerCustomizer() {
            public Object addingService(ServiceReference reference) {
                Definition definition = (Definition)context.getService(reference);
                Element content = definition.getContent();
                Registrations registrations = new Registrations();
                parse(registrations, content);
                return registrations;
            }
            
            public void removedService(ServiceReference reference, Object service) {
                ((Registrations)service).unregister();
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
    
    protected abstract void parse(DefinitionParserContext context, Element content);
}
