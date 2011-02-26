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
package com.googlecode.sisme.core.impl;

import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;

import com.googlecode.sisme.Domain;
import com.googlecode.sisme.Interface;
import com.googlecode.sisme.core.model.InterfaceModel;
import com.googlecode.sisme.framework.definition.processor.Dependency;
import com.googlecode.sisme.framework.definition.processor.ManagedObjectFactory;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessor;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessorContext;

public class InterfaceParser extends JAXBDefinitionProcessor<InterfaceModel> {
    public InterfaceParser(BundleContext context) {
        super(context, new QName("http://sisme.googlecode.com/core", "interface"), InterfaceModel.class);
    }

    @Override
    protected void parse(JAXBDefinitionProcessorContext context, final InterfaceModel model) {
        // TODO: Java 5 generics issue here!
        final Dependency<Domain> domain = context.createDependency(Domain.class, model.getDomain());
        context.addManagedObject(Interface.class.getName(), new ManagedObjectFactory() {
            public Object createObject() {
                return model.build(domain.get());
            }
        });
    }
}
