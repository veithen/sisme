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

import com.google.code.jahath.HostAddress;

public class SocketAddress {
    private final HostAddress host;
    private final int port;
    
    public SocketAddress(HostAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public HostAddress getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}