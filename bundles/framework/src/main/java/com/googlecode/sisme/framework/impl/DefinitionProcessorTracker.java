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

import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.googlecode.sisme.framework.definition.Definition;
import com.googlecode.sisme.framework.definition.processor.DefinitionProcessor;

public class DefinitionProcessorTracker extends ProcessorTracker<Definition,Binder,DefinitionProcessor>{
    public DefinitionProcessorTracker(BundleContext context) {
        super(context, Definition.class, Binder.class, DefinitionProcessor.class);
    }

    @Override
    protected String getSelector(ServiceReference reference) {
        return "(&(" + Definition.P_ELEMENT_NAMESPACE + "=" + reference.getProperty(DefinitionProcessor.P_ELEMENT_NAMESPACE) + ")"
                + "(" + Definition.P_ELEMENT_NAME + "=" + reference.getProperty(DefinitionProcessor.P_ELEMENT_NAME) + "))";
    }

    @Override
    protected Binder createContext(BundleContext targetContext, ServiceReference artifactReference) {
        String namespace = (String)artifactReference.getProperty(Definition.P_NAMESPACE);
        String name = (String)artifactReference.getProperty(Definition.P_NAME);
        return new Binder(targetContext, new QName(namespace, name));
    }
}
