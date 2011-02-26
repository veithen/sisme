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
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;

import com.googlecode.sisme.framework.Document;

public class Deployer extends BundleTracker {
    public Deployer(BundleContext context) {
        super(context, Bundle.ACTIVE, null);
    }

    @Override
    public Object addingBundle(Bundle bundle, BundleEvent event) {
        // No need to unregister the service; this will be done anyway by the OSGi runtime
        // when the bundle is stopped.
        URL url = bundle.getEntry("META-INF/sisme.xml");
        if (url != null) {
            Properties props = new Properties();
            props.setProperty(Document.P_CONTENT_TYPE, Document.CT_DEFINITIONS);
            bundle.getBundleContext().registerService(Document.class.getName(), new StaticDocument(url), props);
        }
        return bundle;
    }
}
