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
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.googlecode.sisme.framework.Processor;

public abstract class ProcessorTracker<T,C extends AbstractProcessorContext,P extends Processor<T,? super C>> extends ServiceTracker {
    private final Class<T> artifactClass;
    private final Class<C> contextClass;
    private final Class<P> processorClass;
    
    public ProcessorTracker(BundleContext context, Class<T> artifactClass, Class<C> contextClass, Class<P> processorClass) {
        super(context, processorClass.getName(), null);
        this.artifactClass = artifactClass;
        this.contextClass = contextClass;
        this.processorClass = processorClass;
    }
    
    @Override
    public Object addingService(ServiceReference reference) {
        P processor = processorClass.cast(context.getService(reference));
        ProcessorInvoker<T,C> invoker = new ProcessorInvoker<T,C>(this, context, artifactClass, contextClass, processor, getSelector(reference));
        invoker.start();
        return invoker;
    }

    protected abstract String getSelector(ServiceReference reference);
    
    protected abstract C createContext(BundleContext targetContext, ServiceReference artifactReference);
    
    @Override
    public void removedService(ServiceReference reference, Object service) {
        ((ProcessorInvoker<?,?>)service).stop();
        context.ungetService(reference);
    }
}
