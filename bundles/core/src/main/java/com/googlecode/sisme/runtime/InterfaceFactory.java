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
import com.googlecode.sisme.framework.ManagedObjectFactory;
import com.googlecode.sisme.framework.ManagedObjectRef;

public class InterfaceFactory extends ManagedObjectFactory {
    private final ManagedObjectRef<Domain> domain;
    private final List<OperationFactory> operations = new ArrayList<OperationFactory>();

    public InterfaceFactory(ManagedObjectRef<Domain> domain) {
        this.domain = domain;
    }

    public void addOperation(OperationFactory operation) {
        operations.add(operation);
    }
}
