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
package com.googlecode.sisme.framework.definition.processor;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import com.googlecode.sisme.framework.ProcessorContext;

public interface DefinitionProcessorContext extends ProcessorContext {
    <T> Dependency<T> createDependency(Class<T> clazz, QName ref, Element content);
    
    // element must represent an XML element of type objectRef
    // TODO
//    <T> Dependency<T> createDependency(Class<T> clazz, Element element);
}
