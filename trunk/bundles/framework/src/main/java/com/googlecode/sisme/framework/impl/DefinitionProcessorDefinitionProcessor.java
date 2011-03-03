/*
 * Copyright 2009-2011 Andreas Veithen
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

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import com.googlecode.sisme.framework.ProcessorException;
import com.googlecode.sisme.framework.component.Definition;
import com.googlecode.sisme.framework.component.DefinitionProcessor;
import com.googlecode.sisme.framework.component.DefinitionProcessorContext;

/**
 * The processor for <code>definitionProcessor</code> definitions. This is one of the bootstrap
 * services of the framework.
 * 
 * @author Andreas Veithen
 */
public class DefinitionProcessorDefinitionProcessor implements DefinitionProcessor {
    public void process(DefinitionProcessorContext context, Definition object) throws ProcessorException {
        Element element = object.getContent();
        String typeString = element.getAttributeNS(null, "type");
        if (typeString == null) {
            throw new ProcessorException("Mandatory attribute 'type' is missing");
        }
        QName type = resolveQName(element, typeString);
        String className = element.getAttributeNS(null, "class");
        if (className.length() == 0) {
            throw new ProcessorException("Mandatory attribute 'class' is missing");
        }
        Class<? extends DefinitionProcessor> clazz;
        try {
            clazz = context.loadClass(className).asSubclass(DefinitionProcessor.class);
        } catch (ClassNotFoundException ex) {
            throw new ProcessorException("The specified class '" + className + "' could not be found.");
        }
        DefinitionProcessor processor;
        try {
            processor = clazz.newInstance();
        } catch (Exception ex) {
            throw new ProcessorException("The class '" + className + "' could not be instantiated");
        }
        Dictionary<String,Object> props = new Hashtable<String,Object>();
        props.put(DefinitionProcessor.P_ELEMENT_NAMESPACE, type.getNamespaceURI());
        props.put(DefinitionProcessor.P_ELEMENT_NAME, type.getLocalPart());
        context.addService(DefinitionProcessor.class.getName(), processor, props);
    }
    
    // TODO: make this reusable
    private QName resolveQName(Element element, String value) {
        int idx = value.indexOf(':');
        if (idx == -1) {
            return new QName(element.lookupNamespaceURI(null), value, "");
        } else {
            String prefix = value.substring(0, idx);
            return new QName(element.lookupNamespaceURI(prefix), value.substring(idx+1), prefix);
        }
    }
}
