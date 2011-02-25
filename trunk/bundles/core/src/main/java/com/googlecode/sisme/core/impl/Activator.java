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

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.googlecode.sisme.core.model.DynamicImportModel;
import com.googlecode.sisme.core.model.InterfaceModel;
import com.googlecode.sisme.core.model.StaticImportModel;
import com.googlecode.sisme.framework.FrameworkSchemaProvider;
import com.googlecode.sisme.framework.jaxb2.JAXBFrameworkSchemaProvider;

public class Activator implements BundleActivator {
    private ServiceRegistration schemaProviderRegistration;
    private InterfaceParser interfaceParser;
    
    public void start(BundleContext context) throws Exception {
        String namespace = "http://sisme.googlecode.com/core";
        FrameworkSchemaProvider schemaProvider = new JAXBFrameworkSchemaProvider(namespace,
                DynamicImportModel.class, InterfaceModel.class, StaticImportModel.class);
        Properties props = new Properties();
        props.put(FrameworkSchemaProvider.P_NAMESPACE, namespace);
        props.put(FrameworkSchemaProvider.P_FILENAME, "core.xsd");
        schemaProviderRegistration = context.registerService(FrameworkSchemaProvider.class.getName(), schemaProvider, props);
        
        interfaceParser = new InterfaceParser(context);
        interfaceParser.start();
    }

    public void stop(BundleContext context) throws Exception {
        interfaceParser.stop();
        schemaProviderRegistration.unregister();
    }
}
