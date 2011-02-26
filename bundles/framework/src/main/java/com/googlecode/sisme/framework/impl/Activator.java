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

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.googlecode.sisme.framework.FrameworkSchemaProvider;
import com.googlecode.sisme.framework.StaticFrameworkSchemaProvider;
import com.googlecode.sisme.framework.document.Document;
import com.googlecode.sisme.framework.document.processor.DocumentProcessor;

public class Activator implements BundleActivator {
    private DocumentProcessorTracker documentProcessorTracker;
    
    public void start(BundleContext context) throws Exception {
        {
            FrameworkSchemaProvider schemaProvider = new StaticFrameworkSchemaProvider(Activator.class.getResource("framework.xsd"));
            Properties props = new Properties();
            props.put(FrameworkSchemaProvider.P_NAMESPACE, "http://sisme.googlecode.com/framework");
            props.put(FrameworkSchemaProvider.P_FILENAME, "framework.xsd");
            context.registerService(FrameworkSchemaProvider.class.getName(), schemaProvider, props);
        }
        {
            Properties props = new Properties();
            props.put(DocumentProcessor.P_SELECTOR, "(" + Document.P_CONTENT_TYPE + "=" + Document.CT_DEFINITIONS + ")");
            context.registerService(DocumentProcessor.class.getName(), new DefinitionsProcessor(), props);
        }
        documentProcessorTracker = new DocumentProcessorTracker(context);
        documentProcessorTracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        documentProcessorTracker.close();
    }
}
