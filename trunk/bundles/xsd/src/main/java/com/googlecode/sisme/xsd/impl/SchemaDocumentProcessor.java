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
package com.googlecode.sisme.xsd.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.googlecode.sisme.framework.ProcessorException;
import com.googlecode.sisme.framework.component.Definition;
import com.googlecode.sisme.framework.document.Document;
import com.googlecode.sisme.framework.document.DocumentProcessor;
import com.googlecode.sisme.framework.document.DocumentProcessorContext;

public class SchemaDocumentProcessor implements DocumentProcessor {
    public void process(DocumentProcessorContext context, Document document) throws ProcessorException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            InputStream in = document.getInputStream();
            try {
                Element element = factory.newDocumentBuilder().parse(in).getDocumentElement();
                // TODO: check that the document actually contains a schema!
                Dictionary<String,Object> props = new Hashtable<String,Object>();
                props.put(Definition.P_ELEMENT_NAMESPACE, "http://www.w3.org/2001/XMLSchema");
                props.put(Definition.P_ELEMENT_NAME, "schema");
                context.addService(Definition.class.getName(), new Definition(element), props);
            } finally {
                in.close();
            }
        } catch (IOException ex) {
            throw new ProcessorException(ex);
        } catch (ParserConfigurationException ex) {
            throw new ProcessorException(ex);
        } catch (SAXException ex) {
            throw new ProcessorException(ex);
        }
    }
}
