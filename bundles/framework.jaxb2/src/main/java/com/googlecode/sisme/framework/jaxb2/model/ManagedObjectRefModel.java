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
package com.googlecode.sisme.framework.jaxb2.model;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

@XmlType(name="objectRef", namespace="http://sisme.googlecode.com/core")
public class ManagedObjectRefModel {
    private QName ref;
    private Element definition;

    @XmlAttribute
    public QName getRef() {
        return ref;
    }

    public void setRef(QName ref) {
        this.ref = ref;
    }

    @XmlAnyElement
    public Element getDefinition() {
        return definition;
    }

    public void setDefinition(Element definition) {
        this.definition = definition;
    }
}
