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
package com.google.code.jahath.common.io;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.google.code.jahath.testutils.CRC;
import com.google.code.jahath.testutils.CRCOutputStream;

public class SwappableOutputStreamTest {
    @Test
    public void test() throws Exception {
        final SwappableOutputStream swappableOutputStream = new SwappableOutputStream("test");
        final CRC actualCRC = new CRC();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (swappableOutputStream.swap(new CRCOutputStream(actualCRC))) {
                        // Just loop
                    }
                } catch (InterruptedException ex) {
                    return;
                }
            }
        });
        thread.start();
        Random random = new Random();
        CRC expectedCRC = new CRC();
        for (int i=0; i<100; i++) {
            int len = 2048 + random.nextInt(4096);
            byte[] data = new byte[len];
            random.nextBytes(data);
            expectedCRC.update(data);
            swappableOutputStream.write(data);
            swappableOutputStream.flush();
        }
        swappableOutputStream.close();
        thread.join();
        Assert.assertEquals(expectedCRC.getValue(), actualCRC.getValue());
    }
}
