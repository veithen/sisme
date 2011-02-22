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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.Document;

import com.googlecode.sisme.framework.FrameworkSchemaProvider;

public class JAXBFrameworkSchemaProvider implements FrameworkSchemaProvider {
    private static class SchemaOutput extends SchemaOutputResolver {
        private final String namespaceUri;
        private final String filename;
        private final Document document;
        
        public SchemaOutput(String namespaceUri, String filename, Document document) {
            this.namespaceUri = namespaceUri;
            this.filename = filename;
            this.document = document;
        }

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            if (this.namespaceUri.equals(namespaceUri)) {
                DOMResult result = new DOMResult(document);
                result.setSystemId(filename);
                return result;
            } else {
                return null;
            }
        }

        public String getFilename() {
            return filename;
        }
    }
    
    private final Document document;
    
    public JAXBFrameworkSchemaProvider(String namespaceUri, String filename, Class<?>... classes) throws JAXBException {
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            throw new Error("Unable to create DOM document", ex);
        }
        JAXBContext context = JAXBUtil.createContext(classes);
        SchemaOutput output = new SchemaOutput(namespaceUri, filename, document);
        try {
            context.generateSchema(output);
        } catch (IOException ex) {
            throw new Error("Unable to generate schema", ex);
        }
        filename = output.getFilename();
    }

    public Document getSchema() {
        return document;
    }
}
