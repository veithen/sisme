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
package com.googlecode.sisme.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.googlecode.sisme.description.Domain;
import com.googlecode.sisme.description.Message;
import com.googlecode.sisme.description.Part;
import com.googlecode.sisme.description.RPCMessage;

@XmlType(name="message")
public class MessageModel {
    private List<PartModel> parts;

    @XmlElement(name="part")
    public List<PartModel> getParts() {
        return parts;
    }

    public void setParts(List<PartModel> parts) {
        this.parts = parts;
    }
    
    public Message build(Domain<?> domain) {
        List<Part> parts = new ArrayList<Part>();
        if (this.parts != null) {
            for (PartModel part : this.parts) {
                parts.add(part.build(domain));
            }
        }
        // TODO: there are some missing pieces here
        return new RPCMessage(parts, null);
    }
}
