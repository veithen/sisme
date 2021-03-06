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
package com.googlecode.sisme.framework.jaxb2;

import com.googlecode.sisme.framework.ObjectFactory;
import com.googlecode.sisme.framework.component.DefinitionProcessorContext;
import com.googlecode.sisme.framework.component.Dependency;
import com.googlecode.sisme.framework.jaxb2.model.ComponentRefModel;

public class JAXBDefinitionProcessorContext {
    private DefinitionProcessorContext parent;

    public JAXBDefinitionProcessorContext(DefinitionProcessorContext parent) {
        this.parent = parent;
    }
    
    public <T> Dependency<T> createDependency(Class<T> clazz, ComponentRefModel model) {
        return parent.createDependency(clazz, model.getRef(), model.getDefinition());
    }

    public void addService(String clazz, Object object) {
        parent.addService(clazz, object);
    }

    public void addService(String clazz, ObjectFactory factory) {
        parent.addService(clazz, factory);
    }
}
