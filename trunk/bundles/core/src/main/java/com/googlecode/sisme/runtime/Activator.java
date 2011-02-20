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

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.googlecode.sisme.core.model.InterfaceModel;
import com.googlecode.sisme.framework.FrameworkSchemaProvider;
import com.googlecode.sisme.framework.jaxb2.JAXBFrameworkSchemaProvider;

public class Activator implements BundleActivator {
    public void start(BundleContext context) throws Exception {
        String namespace = "http://sisme.googlecode.com/core";
        FrameworkSchemaProvider schemaProvider = new JAXBFrameworkSchemaProvider(namespace, InterfaceModel.class);
        Properties props = new Properties();
        props.put("namespace", namespace);
        context.registerService(FrameworkSchemaProvider.class.getName(), schemaProvider, props);
        
//        ProviderUtils.registerManagedObjectFactory(context, new InterfaceFactory(),
//                new QName("http://sisme.googlecode.com/core", "interface"));
    }

    public void stop(BundleContext context) throws Exception {
    }
}
