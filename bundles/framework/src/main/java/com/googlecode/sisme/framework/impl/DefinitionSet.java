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
import java.util.List;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.w3c.dom.Element;

import com.googlecode.sisme.framework.Definition;

public class DefinitionSet {
    private final BundleContext targetBundleContext;
    private final List<ServiceRegistration> registrations = new ArrayList<ServiceRegistration>();
    private String targetNamespace;
    
    public DefinitionSet(BundleContext targetBundleContext) {
        this.targetBundleContext = targetBundleContext;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    public void addDefinition(Element element) {
        Properties props = new Properties();
        props.setProperty(Definition.P_ELEMENT_NAMESPACE, element.getNamespaceURI());
        props.setProperty(Definition.P_ELEMENT_NAME, element.getLocalName());
        props.setProperty(Definition.P_TARGET_NAMESPACE, targetNamespace);
        registrations.add(targetBundleContext.registerService(Definition.class.getName(), new DefinitionImpl(element), props));
    }
    
    public void unregister() {
        for (ServiceRegistration registration : registrations) {
            registration.unregister();
        }
        registrations.clear();
    }
}
