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
package com.googlecode.sisme.jmx.websphere.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.googlecode.sisme.jmx.JMXProvider;

public class WebSphereJMXProvider implements JMXProvider {
    private final ClassLoader classLoader;
    
    public WebSphereJMXProvider(File wasHome) {
        try {
            classLoader = new URLClassLoader(new URL[] {
                    new File(wasHome, "runtimes/com.ibm.ws.admin.client_7.0.0.jar").toURL(),
                    new File(wasHome, "runtimes/com.ibm.ws.ejb.thinclient_7.0.0.jar").toURL(),
                    new File(wasHome, "runtimes/com.ibm.ws.orb_7.0.0.jar").toURL() });
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public JMXConnector newJMXConnector(JMXServiceURL serviceURL) throws IOException {
        Map<String,Object> environment = new HashMap<String,Object>();
        environment.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
        // TODO This is actually not quite correct...
        environment.put(JMXConnectorFactory.DEFAULT_CLASS_LOADER, classLoader);
        environment.put(JMXConnectorFactory.PROTOCOL_PROVIDER_CLASS_LOADER, classLoader);
        // Setting the context class loader is still required to locate the InitialContextFactory
        Thread thread = Thread.currentThread();
        ClassLoader tccl = thread.getContextClassLoader();
        thread.setContextClassLoader(classLoader);
        try {
            return JMXConnectorFactory.newJMXConnector(serviceURL, environment);
        } finally {
            thread.setContextClassLoader(tccl);
        }
    }
}
