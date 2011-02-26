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

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;

import org.osgi.framework.BundleContext;

import com.googlecode.sisme.framework.document.Document;
import com.googlecode.sisme.framework.document.processor.DocumentProcessor;
import com.googlecode.sisme.framework.document.processor.DocumentProcessorContext;

public class DefinitionsProcessor extends DocumentProcessor<DefinitionSet> {
    public DefinitionsProcessor(BundleContext context) {
        super(context, Document.CT_DEFINITIONS);
    }

    @Override
    protected DefinitionSet processDocument(DocumentProcessorContext context, Source source) {
        DefinitionSet definitionSet = new DefinitionSet(context);
        try {
            // TODO: if an exception is thrown, we may already have registered some services
            TransformerFactory.newInstance().newTransformer().transform(source,
                    new SAXResult(new DefinitionsContentHandler(definitionSet)));
        } catch (TransformerException ex) {
            // TODO
            throw new Error(ex);
        }
        return definitionSet;
    }

    @Override
    protected void documentRemoved(DefinitionSet definitionSet) {
    }
}
