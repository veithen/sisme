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
package com.googlecode.sisme.help;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Pretty printer for XML schema documents. This currently only handles indentation. Note that the
 * indentation algorithm in the JAXP implementation provided by Java 1.5 is broken. The same is true
 * for Xalan Serializer 2.7.1. Therefore, it is useful anyway to have our own pretty printer.
 * 
 * @author Andreas Veithen
 */
public class SchemaPrettyPrinter implements ContentHandler {
    private static final Set<String> indentable = new HashSet<String>(Arrays.asList(
            "schema", "element", "complexType", "complexContent", "extension", "sequence",
            "attribute"));
    
    private static final char[] LF = { '\n' };
    private static final char[] SPACES = { ' ', ' ' };
    
    private final ContentHandler parent;
    private Stack<Boolean> indent = new Stack<Boolean>();
    private boolean indentWritten;

    public SchemaPrettyPrinter(ContentHandler parent) {
        this.parent = parent;
    }

    public void setDocumentLocator(Locator locator) {
        parent.setDocumentLocator(locator);
    }

    public void startDocument() throws SAXException {
        parent.startDocument();
    }

    public void endDocument() throws SAXException {
        parent.endDocument();
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        parent.startPrefixMapping(prefix, uri);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        parent.endPrefixMapping(prefix);
    }
    
    private void writeIndent() throws SAXException {
        parent.characters(LF, 0, 1);
        for (int i=0, level=indent.size(); i<level; i++) {
            parent.characters(SPACES, 0, SPACES.length);
        }
        indentWritten = true;
    }
    
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        writeIndent();
        indent.push(namespaceURI.equals("http://www.w3.org/2001/XMLSchema") && indentable.contains(localName));
        parent.startElement(namespaceURI, localName, qName, atts);
        indentWritten = false;
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        indent.pop();
        if (indentWritten) {
            writeIndent();
        }
        parent.endElement(namespaceURI, localName, qName);
        indentWritten = true;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!indent.peek()) {
            parent.characters(ch, start, length);
        }
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        characters(ch, start, length);
    }

    public void processingInstruction(String target, String data) throws SAXException {
        parent.processingInstruction(target, data);
    }

    public void skippedEntity(String name) throws SAXException {
        parent.skippedEntity(name);
    }
}
