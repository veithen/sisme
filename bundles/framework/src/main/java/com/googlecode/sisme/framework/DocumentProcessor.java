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
package com.googlecode.sisme.framework;

import javax.xml.transform.Source;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Tracks documents with a given content type and processes them.
 * 
 * @author Andreas Veithen
 */
public abstract class DocumentProcessor<T> {
    // TODO: if the parsing fails, we should register a FaultyArtifact and store the service registration here
    private class ProcessedDocument {
        private final T object;

        public ProcessedDocument(T object) {
            this.object = object;
        }

        public T getObject() {
            return object;
        }
    }
    
    private class Customizer implements ServiceTrackerCustomizer {
        public Object addingService(ServiceReference reference) {
            Document document = (Document)context.getService(reference);
            return new ProcessedDocument(processDocument(reference.getBundle().getBundleContext(), document.getSource()));
        }

        public void modifiedService(ServiceReference reference, Object service) {
            // TODO Auto-generated method stub
            
        }

        public void removedService(ServiceReference reference, Object service) {
            documentRemoved(((ProcessedDocument)service).getObject());
            context.ungetService(reference);
        }
    }
    
    private final BundleContext context;
    private final ServiceTracker tracker;
    
    public DocumentProcessor(BundleContext context, String contentType) {
        this.context = context;
        Filter filter;
        try {
            filter = context.createFilter("(&(objectClass=" + Document.class.getName() + ")(" + Document.P_CONTENT_TYPE + "=" + contentType + "))");
        } catch (InvalidSyntaxException ex) {
            throw new Error(ex);
        }
        tracker = new ServiceTracker(context, filter, new Customizer());
    }
    
    public void start() {
        tracker.open();
    }
    
    public void stop() {
        tracker.close();
    }
    
    /**
     * 
     * 
     * @param targetContext the bundle context into which all created services should be registered
     * @param source
     * @return
     */
    // TODO: maybe we should have a proxy context here, so that we can safely rollback any registrations done before a parse error occurs
    protected abstract T processDocument(BundleContext targetContext, Source source);
    
    protected abstract void documentRemoved(T object);
}
