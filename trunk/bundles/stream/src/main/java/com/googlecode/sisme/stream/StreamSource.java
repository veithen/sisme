/*
 * Copyright 2009-2011 Andreas Veithen
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

import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSource {
    InputStream getInputStream();
    
    /**
     * Write the data of this source to the given output stream. The call will block until all data
     * has been written to the stream. In order to stream the data in a non blocking way, construct
     * an {@link OutputStreamAdapter} and use {@link #connectTo(StreamSink)}.
     * 
     * @param out
     *            the output stream
     */
    void writeTo(OutputStream out);

    // TODO: for this to work, we may need a thread pool if the source uses non blocking I/O
    void connectTo(StreamSink sink);
}
