/*
 * Copyright 2009-2010 Andreas Veithen
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
package com.google.code.jahath.tcp.impl;

import java.io.IOException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

import com.google.code.jahath.Endpoint;
import com.google.code.jahath.tcp.Gateway;
import com.google.code.jahath.tcp.SocketAddress;
import com.googlecode.sisme.stream.Connection;

public class TcpEndpoint implements Endpoint {
    private final SocketAddress address;
    private final ServiceTracker gatewayTracker;

    public TcpEndpoint(BundleContext bundleContext, SocketAddress address, String gatewayName) {
        this.address = address;
        try {
            gatewayTracker = new ServiceTracker(bundleContext,
                    bundleContext.createFilter("(&(objectClass=" + Gateway.class.getName() + ")(name=" + gatewayName + "))"), null);
            gatewayTracker.open();
        } catch (InvalidSyntaxException ex) {
            throw new Error(ex);
        }
    }

    public Connection connect() throws IOException {
        Gateway gateway = (Gateway)gatewayTracker.getService();
        if (gateway == null) {
            throw new IOException("Gateway not available");
        } else {
            return gateway.connect(address);
        }
    }
    
    public void destroy() {
        gatewayTracker.close();
    }
}
