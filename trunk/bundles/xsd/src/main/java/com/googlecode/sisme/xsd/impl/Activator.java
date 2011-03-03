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
package com.googlecode.sisme.xsd.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.googlecode.sisme.framework.document.Document;
import com.googlecode.sisme.framework.document.DocumentProcessor;

public class Activator implements BundleActivator {
    public void start(BundleContext context) throws Exception {
        Dictionary<String,Object> props = new Hashtable<String,Object>();
        props.put(DocumentProcessor.P_SELECTOR, "(" + Document.P_FILE_TYPE + "=" + "xsd)");
        context.registerService(DocumentProcessor.class.getName(), new SchemaDocumentProcessor(), props);

//        props.put(DefinitionProcessor.P_ELEMENT_NAMESPACE, "http://www.w3.org/2001/XMLSchema");
//        props.put(DefinitionProcessor.P_ELEMENT_NAME, "schema");
    }

    public void stop(BundleContext context) throws Exception {
        // TODO Auto-generated method stub
        
    }
}
