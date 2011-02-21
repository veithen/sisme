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

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="operation", propOrder={"input", "output", "faults"})
public class OperationModel {
    private String name;
    private MessageModel input;
    private MessageModel output;
    private List<MessageModel> faults;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public MessageModel getInput() {
        return input;
    }

    public void setInput(MessageModel input) {
        this.input = input;
    }

    @XmlElement
    public MessageModel getOutput() {
        return output;
    }

    public void setOutput(MessageModel output) {
        this.output = output;
    }

    @XmlElement(name="fault")
    public List<MessageModel> getFaults() {
        return faults;
    }

    public void setFaults(List<MessageModel> faults) {
        this.faults = faults;
    }
}
