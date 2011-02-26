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
import java.util.UUID;

import org.w3c.dom.Element;

import com.googlecode.sisme.framework.definition.Definition;
import com.googlecode.sisme.framework.document.processor.DocumentProcessorContext;

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
        Properties props = new Properties();
        props.setProperty(Definition.P_ELEMENT_NAMESPACE, element.getNamespaceURI());
        props.setProperty(Definition.P_ELEMENT_NAME, element.getLocalName());
        props.setProperty(Definition.P_NAMESPACE, targetNamespace);
        String name = element.getAttributeNS(null, "name");
        if (name.length() == 0) {
            name = UUID.randomUUID().toString();
        }
        props.setProperty(Definition.P_NAME, name);
        context.registerService(Definition.class.getName(), new DefinitionImpl(element), props);
    }
}
