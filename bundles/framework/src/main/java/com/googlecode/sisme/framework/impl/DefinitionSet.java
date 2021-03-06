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

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

import org.w3c.dom.Element;

import com.googlecode.sisme.framework.component.Definition;
import com.googlecode.sisme.framework.document.DocumentProcessorContext;

public class DefinitionSet {
    private final DocumentProcessorContext context;
    private String targetNamespace;
    
    public DefinitionSet(DocumentProcessorContext context) {
        this.context = context;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    public void addDefinition(Element element) {
        Dictionary<String,Object> props = new Hashtable<String,Object>();
        props.put(Definition.P_ELEMENT_NAMESPACE, element.getNamespaceURI());
        props.put(Definition.P_ELEMENT_NAME, element.getLocalName());
        props.put(Definition.P_NAMESPACE, targetNamespace);
        String name = element.getAttributeNS(null, "name");
        if (name.length() == 0) {
            name = UUID.randomUUID().toString();
        }
        props.put(Definition.P_NAME, name);
        context.addService(Definition.class.getName(), new Definition(element), props);
    }
}
