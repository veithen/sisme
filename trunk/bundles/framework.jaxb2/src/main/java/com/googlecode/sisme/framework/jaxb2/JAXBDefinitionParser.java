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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.googlecode.sisme.framework.AbstractDefinitionParser;

public abstract class JAXBDefinitionParser extends AbstractDefinitionParser {
    private class SchemaBuilder extends SchemaOutputResolver {
        private final DocumentBuilder documentBuilder;
        private final List<Document> schemaDocuments = new ArrayList<Document>();
        
        public SchemaBuilder() {
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                throw new Error("Unable to create document builder");
            }
        }

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            Document document = documentBuilder.newDocument();
            schemaDocuments.add(document);
            DOMResult result = new DOMResult(document);
            result.setSystemId(suggestedFileName);
            return result;
        }
        
        public Schema buildSchema() throws SAXException {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            List<Source> schemaSources = new ArrayList<Source>();
            for (Document document : schemaDocuments) {
                schemaSources.add(new DOMSource(document));
            }
            return factory.newSchema(schemaSources.toArray(new Source[schemaSources.size()]));
        }
    }
    
    private final JAXBContext jaxbContext;
    private final Schema schema;
    
    public JAXBDefinitionParser(Class<?> elementClass) {
        try {
            jaxbContext = JAXBUtil.createContext(elementClass);
        } catch (JAXBException ex) {
            throw new Error("Unable to create JAXBContext for " + elementClass, ex);
        }
        try {
            SchemaBuilder schemaBuilder = new SchemaBuilder();
            jaxbContext.generateSchema(schemaBuilder);
            schema = schemaBuilder.buildSchema();
        } catch (Exception ex) {
            throw new Error("Unable to generate schema for " + elementClass, ex);
        }
    }

    public final Schema getSchema() {
        return schema;
    }
}
