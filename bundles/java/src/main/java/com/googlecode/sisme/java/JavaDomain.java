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
package com.googlecode.sisme.java;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.sisme.description.Domain;

public class JavaDomain implements Domain<JavaType> {
    private final JavaDomain parent;
    private final ClassLoader classLoader;
    private final Map<String,JavaType> types = new HashMap<String,JavaType>();

    public JavaDomain(JavaDomain parent, ClassLoader classLoader) {
        this.parent = parent;
        this.classLoader = classLoader;
    }

    public synchronized JavaType lookup(String name) {
        JavaType type = parent == null ? null : parent.lookup(name);
        if (type != null) {
            return type;
        } else {
            type = types.get(name);
            if (type == null) {
                Class<?> clazz;
                try {
                    clazz = classLoader.loadClass(name);
                } catch (ClassNotFoundException ex) {
                    return null;
                }
                type = new JavaType(clazz);
                types.put(name, type);
            }
            return type;
        }
    }
}
