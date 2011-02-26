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

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.googlecode.sisme.framework.Processor;

public class ProcessorInvoker<T,C extends AbstractProcessorContext> {
    private class Customizer implements ServiceTrackerCustomizer {
        public Object addingService(ServiceReference reference) {
            T artifact = artifactClass.cast(context.getService(reference));
            C processorContext = processorTracker.createContext(reference.getBundle().getBundleContext(), reference);
            processor.process(processorContext, artifact);
            processorContext.start();
            return processorContext;
        }

        public void modifiedService(ServiceReference reference, Object service) {
            // TODO Auto-generated method stub
            
        }

        public void removedService(ServiceReference reference, Object service) {
            C processorContext = contextClass.cast(service);
            processorContext.stop();
            context.ungetService(reference);
        }
    }
    
    private final ProcessorTracker<T,C,? extends Processor<T,? super C>> processorTracker;
    private final BundleContext context;
    private final Class<T> artifactClass;
    private final Class<C> contextClass;
    private final Processor<T,? super C> processor;
    private final ServiceTracker tracker;
    
    public ProcessorInvoker(ProcessorTracker<T,C,? extends Processor<T,? super C>> processorTracker, BundleContext context, Class<T> artifactClass, Class<C> contextClass, Processor<T,? super C> processor, String selector) {
        this.processorTracker = processorTracker;
        this.context = context;
        this.artifactClass = artifactClass;
        this.contextClass = contextClass;
        this.processor = processor;
        Filter filter;
        try {
            filter = context.createFilter("(&(objectClass=" + artifactClass.getName() + ")" + selector + ")");
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
}
