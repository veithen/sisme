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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class DefinitionsContentHandler implements ContentHandler {
    private DefinitionSet definitionSet;
    private int level;
    private Document definitionDocument;
    private TransformerHandler definitionContentHandler;
    
    public DefinitionsContentHandler(DefinitionSet definitionSet) {
        this.definitionSet = definitionSet;
    }

    public void setDocumentLocator(Locator locator) {
    }
    
    public void startDocument() throws SAXException {
    }
    
    public void endDocument() throws SAXException {
    }
    
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        if (definitionContentHandler != null) {
            definitionContentHandler.startPrefixMapping(prefix, uri);
        }
    }
    
    public void endPrefixMapping(String prefix) throws SAXException {
        if (definitionContentHandler != null) {
            definitionContentHandler.endPrefixMapping(prefix);
        }
    }
    
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (level == 0) {
            String targetNamespace = atts.getValue("", "targetNamespace");
            if (targetNamespace == null) {
                throw new SAXException("No targetNamespace attribute found");
            }
            definitionSet.setTargetNamespace(targetNamespace);
        } else if (level == 1) {
            try {
                definitionDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            } catch (ParserConfigurationException ex) {
                throw new Error(ex);
            }
            try {
                definitionContentHandler = ((SAXTransformerFactory)TransformerFactory.newInstance()).newTransformerHandler();
            } catch (TransformerException ex) {
                throw new Error(ex);
            }
            definitionContentHandler.setResult(new DOMResult(definitionDocument));
            definitionContentHandler.startDocument();
        }
        if (level != 0) {
            definitionContentHandler.startElement(uri, localName, qName, atts);
        }
        level++;
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        level--;
        if (level != 0) {
            definitionContentHandler.endElement(uri, localName, qName);
        }
        if (level == 1) {
            definitionContentHandler.endDocument();
            definitionSet.addDefinition(definitionDocument.getDocumentElement());
            definitionDocument = null;
            definitionContentHandler = null;
        }
    }
    
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (definitionContentHandler != null) {
            definitionContentHandler.characters(ch, start, length);
        }
    }
    
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        if (definitionContentHandler != null) {
            definitionContentHandler.ignorableWhitespace(ch, start, length);
        }
    }
    
    public void processingInstruction(String target, String data) throws SAXException {
        if (definitionContentHandler != null) {
            definitionContentHandler.processingInstruction(target, data);
        }
    }
    
    public void skippedEntity(String name) throws SAXException {
        if (definitionContentHandler != null) {
            definitionContentHandler.skippedEntity(name);
        }
    }
}
