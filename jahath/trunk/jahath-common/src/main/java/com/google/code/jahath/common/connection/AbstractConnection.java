/*
 * Copyright 2009 Andreas Veithen
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
package com.google.code.jahath.common.connection;

import java.io.IOException;

public abstract class AbstractConnection implements Connection {
    private State state = State.OPEN;

    public final synchronized void close() throws IOException {
        state = State.CLOSING;
        doClose();
        state = State.CLOSED;
    }

    /**
     * Perform the close operation for this connection.
     * 
     * @throws IOException
     */
    protected abstract void doClose() throws IOException;

    public final synchronized State getState() {
        return state;
    }
}
