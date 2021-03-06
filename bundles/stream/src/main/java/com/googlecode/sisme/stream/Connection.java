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
package com.googlecode.sisme.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Models a bidirectional stream oriented connection as seen by one of the endpoints. This interface
 * is a generalization of {@link java.net.Socket}.
 * <p>
 * Note that implementations should extend {@link AbstractConnection} instead of implementing this
 * interface directly.
 * 
 * @author Andreas Veithen
 */
// TODO: specify what should happen if close() is called on the InputStream or OutputStream returned by instances of this class
public interface Connection {
    public enum State {
        /**
         * The connection is open.
         */
        OPEN,
        
        /**
         * The connection is being closed. This means that {@link Connection#close()} has been
         * called, but that the connection has not yet been closed. This state is useful to
         * distinguish exceptions that are thrown (by methods of the instances returned by
         * {@link Connection#getInputStream()} and {@link Connection#getOutputStream()}) because the
         * connection is being closed.
         */
        CLOSING,
        
        /**
         * The connection has been closed.
         */
        CLOSED
    }
    
    /**
     * Get a stream source for this connection. The returned object can be used to read data sent by
     * the other end of the connection.
     * 
     * @return The stream source for the connection. For a given connection, multiple calls to this
     * method will return the same instance.
     * @throws IOException 
     */
    StreamSource getStreamSource() throws IOException;
    
    /**
     * Get a stream sink for this connection. The returned object can be used to transmit data to
     * the other end of the connection.
     * 
     * @return The stream sink for the connection. For a given connection, multiple calls to this
     * method will return the same instance.
     * @throws IOException 
     */
    StreamSink getStreamSink() throws IOException;
    
    /**
     * Close this connection. This will first put the connection into state {@link State#CLOSING}
     * and then attempt to close the connection. After successful completion of the close operation,
     * the state will be {@link State#CLOSED}.
     * <p>
     * Any thread blocked reading from the input stream returned by {@link #getStreamSource()} will
     * receive a {@link ConnectionClosedLocallyException}.
     * 
     * @throws IOException
     */
    void close() throws IOException;
    
    /**
     * Get the state of this connection.
     * 
     * @return the state of the connection
     */
    State getState();
    
    /**
     * Determine if this connection has been established using a secure channel (such as SSL).
     * 
     * @return
     */
    boolean isSecure();
}
