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
package com.googlecode.sisme.provider;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

import com.sun.xml.bind.api.JAXBRIContext;

public abstract class JAXBDefinitionParser extends AbstractDefinitionParser {
    private final JAXBRIContext jaxbContext;
    private final Schema schema;
    
    public JAXBDefinitionParser(Class<?> elementClass) {
        try {
            jaxbContext = (JAXBRIContext)JAXBContext.newInstance(elementClass);
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
