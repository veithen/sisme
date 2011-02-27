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
package com.googlecode.sisme.jmx.impl;

import java.net.MalformedURLException;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;

import com.googlecode.sisme.framework.ObjectFactory;
import com.googlecode.sisme.framework.ProcessorException;
import com.googlecode.sisme.framework.definition.processor.Dependency;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessor;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessorContext;
import com.googlecode.sisme.jmx.JMXProvider;
import com.googlecode.sisme.jmx.model.ConnectionModel;

public class ConnectionDefinitionProcessor extends JAXBDefinitionProcessor<ConnectionModel> {
    public ConnectionDefinitionProcessor() {
        super(ConnectionModel.class);
    }

    @Override
    protected void parse(JAXBDefinitionProcessorContext context, ConnectionModel model) throws ProcessorException {
        final JMXServiceURL serviceURL;
        try {
            serviceURL = new JMXServiceURL(model.getUrl());
        } catch (MalformedURLException ex) {
            throw new ProcessorException(ex);
        }
        final Dependency<JMXProvider> provider = context.createDependency(JMXProvider.class, model.getProvider());
        context.addService(MBeanServerConnection.class.getName(), new ObjectFactory() {
            public Object createObject() {
                // TODO: we need to use a managed component here
                try {
                    JMXConnector connector = provider.get().newJMXConnector(serviceURL);
                    connector.connect();
                    return connector.getMBeanServerConnection();
                } catch (Exception ex) {
                    // TODO
                    throw new Error(ex);
                }
            }
        });
    }
}
