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
package com.googlecode.sisme.framework.parser;

import org.osgi.framework.BundleContext;
import org.w3c.dom.Element;

class Binder implements DefinitionParserContext {
    private final BundleContext bundleContext;

    public Binder(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }

    public <T> Dependency<T> createDependency(Class<T> clazz, Element element) {
        // TODO Auto-generated method stub
        return null;
    }

    public void addManagedObject(String clazz, Object object) {
        addManagedObjectFactory(clazz, new ManagedObjectWrapper(object));
    }

    public void addManagedObjectFactory(String clazz, ManagedObjectFactory factory) {
        // TODO Auto-generated method stub
        
    }

}
