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
package com.google.code.jahath.tcp;

import java.io.IOException;

import com.google.code.jahath.Connection;
import com.google.code.jahath.Endpoint;
import com.google.code.jahath.Gateway;
import com.google.code.jahath.SocketAddress;

public class TcpEndpoint implements Endpoint {
    private final SocketAddress address;
    private final Gateway gateway;

    public TcpEndpoint(SocketAddress address, Gateway gateway) {
        this.address = address;
        this.gateway = gateway;
    }

    public Connection connect() throws IOException {
        return gateway.connect(address);
    }
}
