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

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import com.googlecode.sisme.framework.ProcessorException;
import com.googlecode.sisme.framework.document.Document;
import com.googlecode.sisme.framework.document.processor.DocumentProcessor;
import com.googlecode.sisme.framework.document.processor.DocumentProcessorContext;

public class DefinitionsProcessor implements DocumentProcessor {
    public void process(DocumentProcessorContext context, Document document) throws ProcessorException {
        DefinitionSet definitionSet = new DefinitionSet(context);
        try {
            InputStream in = document.getInputStream();
            try {
                TransformerFactory.newInstance().newTransformer().transform(new StreamSource(in),
                        new SAXResult(new DefinitionsContentHandler(definitionSet)));
            } finally {
                in.close();
            }
        } catch (TransformerException ex) {
            throw new ProcessorException(ex);
        } catch (IOException ex) {
            throw new ProcessorException(ex);
        }
    }
}
