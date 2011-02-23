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
package com.googlecode.sisme.runtime;

import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;

import com.googlecode.sisme.core.model.InterfaceModel;
import com.googlecode.sisme.description.Domain;
import com.googlecode.sisme.description.Interface;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionParser;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionParserContext;
import com.googlecode.sisme.framework.parser.Dependency;
import com.googlecode.sisme.framework.parser.ManagedObjectFactory;

public class InterfaceParser extends JAXBDefinitionParser<InterfaceModel> {
    public InterfaceParser(BundleContext context) {
        super(context, new QName("http://sisme.googlecode.com/core", "interface"), InterfaceModel.class);
    }

    @Override
    protected void parse(JAXBDefinitionParserContext context, final InterfaceModel model) {
        // TODO: Java 5 generics issue here!
        final Dependency<Domain> domain = context.createDependency(Domain.class, model.getDomain());
        context.addManagedObject(Interface.class.getName(), new ManagedObjectFactory() {
            public Object createObject() {
                return model.build(domain.get());
            }
        });
    }
}
