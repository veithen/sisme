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

import com.googlecode.sisme.framework.jaxb2.model.ManagedObjectRefModel;
import com.googlecode.sisme.framework.parser.DefinitionParserContext;
import com.googlecode.sisme.framework.parser.Dependency;
import com.googlecode.sisme.framework.parser.ManagedObjectFactory;

public class JAXBDefinitionParserContext {
    private DefinitionParserContext parent;

    public JAXBDefinitionParserContext(DefinitionParserContext parent) {
        this.parent = parent;
    }
    
    public <T> Dependency<T> createDependency(Class<T> clazz, ManagedObjectRefModel model) {
        return parent.createDependency(clazz, model.getRef(), model.getDefinition());
    }

    public void addManagedObject(String clazz, Object object) {
        parent.addManagedObject(clazz, object);
    }

    public void addManagedObjectFactory(String clazz, ManagedObjectFactory factory) {
        parent.addManagedObjectFactory(clazz, factory);
    }
}
