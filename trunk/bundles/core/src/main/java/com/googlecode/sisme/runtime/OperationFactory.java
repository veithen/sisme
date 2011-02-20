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
package com.googlecode.sisme.runtime;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.sisme.description.Domain;
import com.googlecode.sisme.description.Message;
import com.googlecode.sisme.description.Operation;

public class OperationFactory {
    private String name;
    private MessageFactory input;
    private MessageFactory output;
    private final List<MessageFactory> faults = new ArrayList<MessageFactory>();
    
    public void setName(String name) {
        this.name = name;
    }

    public void setInput(MessageFactory input) {
        this.input = input;
    }

    public void setOutput(MessageFactory output) {
        this.output = output;
    }
    
    public void addFault(MessageFactory fault) {
        faults.add(fault);
    }

    public Operation build(Domain domain) {
        List<Message> faults = new ArrayList<Message>(this.faults.size());
        for (MessageFactory fault : this.faults) {
            faults.add(fault.build(domain));
        }
        return new Operation(name, input == null ? null : input.build(domain),
                output == null ? null : output.build(domain), faults);
    }
}
