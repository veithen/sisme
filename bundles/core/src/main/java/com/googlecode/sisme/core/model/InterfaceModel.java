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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.googlecode.sisme.description.Domain;
import com.googlecode.sisme.description.Interface;
import com.googlecode.sisme.description.Operation;
import com.googlecode.sisme.framework.jaxb2.model.ManagedObjectModel;
import com.googlecode.sisme.framework.jaxb2.model.ManagedObjectRefModel;

@XmlRootElement(name="interface")
@XmlType(name="", propOrder={"domain", "operations"})
public class InterfaceModel extends ManagedObjectModel {
    private ManagedObjectRefModel domain;
    private List<OperationModel> operations;

    @XmlElement
    public ManagedObjectRefModel getDomain() {
        return domain;
    }

    public void setDomain(ManagedObjectRefModel domain) {
        this.domain = domain;
    }

    @XmlElement(name="operation")
    public List<OperationModel> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationModel> operations) {
        this.operations = operations;
    }
    
    public Interface build(Domain<?> domain) {
        List<Operation> operations = new ArrayList<Operation>();
        if (this.operations != null) {
            for (OperationModel operation : this.operations) {
                operations.add(operation.build(domain));
            }
        }
        return new Interface(operations);
    }
}
