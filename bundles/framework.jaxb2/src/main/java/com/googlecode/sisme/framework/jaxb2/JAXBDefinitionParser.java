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
import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;
import org.w3c.dom.Element;

import com.googlecode.sisme.framework.parser.DefinitionParser;
import com.googlecode.sisme.framework.parser.DefinitionParserContext;

public abstract class JAXBDefinitionParser<T> extends DefinitionParser {
    private final JAXBContext jaxbContext;
    private final Class<T> elementClass;
    
    // TODO: it should not be necessary to provide the element QName
    public JAXBDefinitionParser(BundleContext context, QName elementQName, Class<T> elementClass) {
        super(context, elementQName);
        this.elementClass = elementClass;
        try {
            jaxbContext = JAXBUtil.createContext(elementClass);
        } catch (JAXBException ex) {
            throw new Error("Unable to create JAXBContext for " + elementClass, ex);
        }
    }

    @Override
    protected final void parse(DefinitionParserContext context, Element content) {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            // "Default" here refers to JAXB 1.0. JAXB 2.0 is more lenient by default.
            unmarshaller.setEventHandler(new DefaultValidationEventHandler());
            parse(new JAXBDefinitionParserContext(context), elementClass.cast(unmarshaller.unmarshal(content)));
        } catch (JAXBException ex) {
            // TODO: implement appropriate error handling in DefinitionParser
            throw new RuntimeException(ex);
        }
    }
    
    protected abstract void parse(JAXBDefinitionParserContext context, T model);
}
