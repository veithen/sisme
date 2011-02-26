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

import com.googlecode.sisme.framework.document.processor.DocumentProcessor;

public class DocumentProcessorTracker extends ServiceTracker {
    public DocumentProcessorTracker(BundleContext context) {
        super(context, DocumentProcessor.class.getName(), null);
    }

    @Override
    public Object addingService(ServiceReference reference) {
        DocumentProcessor processor = (DocumentProcessor)context.getService(reference);
        DocumentProcessorInvoker invoker = createInvoker(processor, (String)reference.getProperty(DocumentProcessor.P_SELECTOR));
        invoker.start();
        return invoker;
    }
    
    private DocumentProcessorInvoker createInvoker(DocumentProcessor processor, String selector) {
        return new DocumentProcessorInvoker(context, processor, selector);
    }
    
    @Override
    public void removedService(ServiceReference reference, Object service) {
        ((DocumentProcessorInvoker)service).stop();
        context.ungetService(reference);
    }
}
