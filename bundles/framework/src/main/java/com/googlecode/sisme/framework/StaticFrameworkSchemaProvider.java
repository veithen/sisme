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
package com.googlecode.sisme.framework;

import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

// TODO: doesn't support ImportResolver yet
public class StaticFrameworkSchemaProvider implements FrameworkSchemaProvider {
	private final Document schema;

	public StaticFrameworkSchemaProvider(URL url) {
		try {
			schema = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException ex) {
			throw new Error(ex);
		}
		try {
			TransformerFactory.newInstance().newTransformer().transform(new StreamSource(url.toExternalForm()), new DOMResult(schema));
		} catch (TransformerException ex) {
			// TODO: we may get here if the user supplied url is incorrect or the document not well formed
			throw new Error(ex);
		}
	}

	public Document getSchema(ImportResolver importResolver) {
		return schema;
	}
}
