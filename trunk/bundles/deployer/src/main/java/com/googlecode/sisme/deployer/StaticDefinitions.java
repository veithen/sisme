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
package com.googlecode.sisme.deployer;

import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.googlecode.sisme.framework.Definitions;

public class StaticDefinitions implements Definitions {
    private final URL url;

    public StaticDefinitions(URL url) {
        this.url = url;
    }

    public Source getSource() {
        return new StreamSource(url.toExternalForm());
    }
}
