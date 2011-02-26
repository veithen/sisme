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

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.googlecode.sisme.framework.Document;

public class DefinitionsTrackerCustomizer implements ServiceTrackerCustomizer {
    private final BundleContext context;
    
    public DefinitionsTrackerCustomizer(BundleContext context) {
        this.context = context;
    }

    public Object addingService(ServiceReference reference) {
        Document definitions = (Document)context.getService(reference);
        DefinitionSet definitionSet = new DefinitionSet(reference.getBundle().getBundleContext());
        try {
            // TODO: if an exception is thrown, we may already have registered some services
            TransformerFactory.newInstance().newTransformer().transform(definitions.getSource(),
                    new SAXResult(new DefinitionsContentHandler(definitionSet)));
        } catch (TransformerException ex) {
            // TODO
            throw new Error(ex);
        }
        return definitionSet;
    }

    public void modifiedService(ServiceReference reference, Object service) {
        // TODO Auto-generated method stub
        
    }

    public void removedService(ServiceReference reference, Object service) {
        ((DefinitionSet)service).unregister();
        context.ungetService(reference);
    }
}
