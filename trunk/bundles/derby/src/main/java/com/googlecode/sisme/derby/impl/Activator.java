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
package com.googlecode.sisme.derby.impl;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.googlecode.sisme.derby.model.DatabaseModel;
import com.googlecode.sisme.framework.FrameworkSchemaProvider;
import com.googlecode.sisme.framework.jaxb2.JAXBFrameworkSchemaProvider;

public class Activator implements BundleActivator {
    private DatabaseManager databaseManager;
    private DatabaseDefinitionParser databaseDefinitionParser;
    private ServiceRegistration schemaProviderRegistration;
    
	public void start(BundleContext context) throws Exception {
	    databaseManager = new DatabaseManager(context);
	    databaseManager.start();
	    databaseDefinitionParser = new DatabaseDefinitionParser(context, databaseManager);
	    databaseDefinitionParser.start();
	    Properties props = new Properties();
	    props.put(FrameworkSchemaProvider.P_NAMESPACE, "http://sisme.googlecode.com/derby");
	    props.put(FrameworkSchemaProvider.P_FILENAME, "derby.xsd");
	    schemaProviderRegistration =
    	    context.registerService(FrameworkSchemaProvider.class.getName(),
    	            new JAXBFrameworkSchemaProvider("http://sisme.googlecode.com/derby", DatabaseModel.class), props);
	}

	public void stop(BundleContext context) throws Exception {
	    schemaProviderRegistration.unregister();
	    databaseDefinitionParser.stop();
	    databaseManager.stop();
	}
}
