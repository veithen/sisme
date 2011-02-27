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
package com.googlecode.sisme.framework.jaxb2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import com.googlecode.sisme.framework.ProcessorException;
import com.googlecode.sisme.framework.definition.Definition;
import com.googlecode.sisme.framework.definition.processor.DefinitionProcessor;
import com.googlecode.sisme.framework.definition.processor.DefinitionProcessorContext;

public abstract class JAXBDefinitionProcessor<T> implements DefinitionProcessor {
    private final JAXBContext jaxbContext;
    private final Class<T> elementClass;
    
    public JAXBDefinitionProcessor(Class<T> elementClass) {
        this.elementClass = elementClass;
        try {
            jaxbContext = JAXBUtil.createContext(elementClass);
        } catch (JAXBException ex) {
            throw new Error("Unable to create JAXBContext for " + elementClass, ex);
        }
    }

    public final void process(DefinitionProcessorContext context, Definition definition) throws ProcessorException {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            // "Default" here refers to JAXB 1.0. JAXB 2.0 is more lenient by default.
            unmarshaller.setEventHandler(new DefaultValidationEventHandler());
            parse(new JAXBDefinitionProcessorContext(context), elementClass.cast(unmarshaller.unmarshal(definition.getContent())));
        } catch (JAXBException ex) {
            throw new ProcessorException(ex);
        }
    }
    
    protected abstract void parse(JAXBDefinitionProcessorContext context, T model) throws ProcessorException;
}
