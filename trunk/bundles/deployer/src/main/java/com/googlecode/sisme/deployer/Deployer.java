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
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.googlecode.sisme.framework.Definitions;

public class Deployer implements BundleListener {
    public void bundleChanged(BundleEvent event) {
        switch (event.getType()) {
            case BundleEvent.STARTED:
                registerDefinitions(event.getBundle());
                break;
            case BundleEvent.STOPPING:
                // TODO
                break;
        }
    }
    
    private void registerDefinitions(Bundle bundle) {
        URL url = bundle.getEntry("META-INF/sisme.xml");
        if (url != null) {
            bundle.getBundleContext().registerService(Definitions.class.getName(), new StaticDefinitions(url), new Properties());
        }
    }
}
