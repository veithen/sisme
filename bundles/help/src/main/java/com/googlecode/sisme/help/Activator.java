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
package com.googlecode.sisme.help;

import java.util.Properties;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.googlecode.sisme.framework.schema.FrameworkSchemaProvider;

public class Activator implements BundleActivator {
    public void start(BundleContext context) throws Exception {
        ServiceTracker tracker = new ServiceTracker(context, FrameworkSchemaProvider.class.getName(), null);
        tracker.open();
        Properties props = new Properties();
        props.put("alias", "/sisme/schema");
        context.registerService(Servlet.class.getName(), new SchemaServlet(tracker), props);
    }

    public void stop(BundleContext context) throws Exception {
    }
}
