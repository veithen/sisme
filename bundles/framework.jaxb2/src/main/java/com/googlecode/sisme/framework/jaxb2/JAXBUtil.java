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

import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.sun.xml.bind.v2.ContextFactory;

// We can't use the standard way of creating JAXBContexts because this would cause class loader issues
final class JAXBUtil {
    private JAXBUtil() {}
    
    public static JAXBContext createContext(Class<?>... classes) throws JAXBException {
        return ContextFactory.createContext(classes, Collections.<String,Object>emptyMap());
    }
    
    public static JAXBContext createContext(String contextPath, ClassLoader classLoader) throws JAXBException {
        return ContextFactory.createContext(contextPath, classLoader, Collections.<String,Object>emptyMap());
    }
}
