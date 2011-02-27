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

import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;

import com.googlecode.sisme.framework.definition.Definition;
import com.googlecode.sisme.framework.definition.processor.DefinitionProcessor;
import com.googlecode.sisme.framework.definition.processor.DefinitionProcessorContext;

public class SchemaDefinitionProcessor implements DefinitionProcessor {
    public void process(DefinitionProcessorContext context, Definition definition) {
        XmlSchemaCollection schemaCollection = new XmlSchemaCollection();
        XmlSchema schema = schemaCollection.read(definition.getContent());
        
    }
}
