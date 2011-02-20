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
package com.googlecode.sisme.description;

import java.util.HashMap;
import java.util.Map;

public class TypeMappingRepository {
    private final Map<TypeMappingKey,TypeMapping> mappings = new HashMap<TypeMappingKey,TypeMapping>();
    
    public TypeMappingRepository() {
//        addTypeMapping(XmlSimpleType.XSD_STRING, new JavaType(String.class), new TypeMapping() {
//            public Object map(Object value) {
//                return value;
//            }
//        });
    }
    
    public void addTypeMapping(Type from, Type to, TypeMapping mapping) {
        mappings.put(new TypeMappingKey(from, to), mapping);
    }
}
