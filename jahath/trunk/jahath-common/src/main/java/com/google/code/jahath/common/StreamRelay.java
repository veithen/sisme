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
package com.google.code.jahath.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;

public class StreamRelay implements Runnable {
    private final Log log;
    private final String label;
    private final InputStream in;
    private final OutputStream out;
    
    public StreamRelay(Log log, String label, InputStream in, OutputStream out) {
        this.log = log;
        this.label = label;
        this.in = in;
        this.out = out;
    }
    
    public void run() {
        byte buf[] = new byte[4096];
        try {
            int n;
            while ((n = in.read(buf)) != -1) {
                if (log.isDebugEnabled()) {
                    HexDump.log(log, label, buf, 0, n);
                }
                out.write(buf, 0, n);
                out.flush();
            }
        } catch (IOException ex) {
            log.error(label, ex);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        log.info(label + " closed");
    }
}
