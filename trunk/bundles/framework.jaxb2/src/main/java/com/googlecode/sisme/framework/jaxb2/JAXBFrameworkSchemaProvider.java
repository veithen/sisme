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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.googlecode.sisme.framework.FrameworkSchemaProvider;
import com.googlecode.sisme.framework.ImportResolver;

public class JAXBFrameworkSchemaProvider implements FrameworkSchemaProvider {
    private static class SchemaOutput extends SchemaOutputResolver {
        private final String namespaceUri;
        private final ImportResolver importResolver;
        private final Document document;
        
        public SchemaOutput(String namespaceUri, ImportResolver importResolver, Document document) {
            this.namespaceUri = namespaceUri;
            this.importResolver = importResolver;
            this.document = document;
        }

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            Result result;
            if (this.namespaceUri.equals(namespaceUri)) {
                result = new DOMResult(document);
            } else {
                // TODO: there may be better ways to discard the output...
                result = new StreamResult(new ByteArrayOutputStream());
            }
            result.setSystemId(importResolver.getLocation(namespaceUri));
            return result;
        }
    }
    
    private final JAXBContext context;
    private final String namespaceUri;
    
    public JAXBFrameworkSchemaProvider(String namespaceUri, Class<?>... classes) throws JAXBException {
        context = JAXBUtil.createContext(classes);
        this.namespaceUri = namespaceUri;
    }

    public Document getSchema(ImportResolver importResolver) {
        Document document;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            throw new Error("Unable to create DOM document", ex);
        }
        SchemaOutput output = new SchemaOutput(namespaceUri, importResolver, document);
        try {
            context.generateSchema(output);
        } catch (IOException ex) {
            throw new Error("Unable to generate schema", ex);
        }
        return document;
    }
}
