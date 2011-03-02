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
package com.googlecode.sisme.framework.impl;

import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;

import com.googlecode.sisme.framework.document.Document;

public class Deployer extends BundleTracker {
    public Deployer(BundleContext context) {
        super(context, Bundle.ACTIVE, null);
    }

    @Override
    public Object addingBundle(Bundle bundle, BundleEvent event) {
        // No need to unregister the service; this will be done anyway by the OSGi runtime
        // when the bundle is stopped.
        Enumeration<?> e = bundle.getEntryPaths("META-INF/sisme/");
        if (e != null) {
            while (e.hasMoreElements()) {
                String path = (String)e.nextElement();
                if (!path.endsWith("/")) {
                    int idx = path.lastIndexOf('.');
                    if (idx != -1) {
                        Properties props = new Properties();
                        URL url = bundle.getEntry(path);
                        props.setProperty(Document.P_LOCATION, url.toExternalForm());
                        props.setProperty(Document.P_FILE_TYPE, path.substring(idx+1));
                        if (path.endsWith("/sisme.xml")) {
                            props.setProperty(Document.P_CONTENT_TYPE, Document.CT_DEFINITIONS);
                        }
                        bundle.getBundleContext().registerService(Document.class.getName(), new StaticDocument(url), props);
                    }
                }
            }
        }
        return bundle;
    }
}
