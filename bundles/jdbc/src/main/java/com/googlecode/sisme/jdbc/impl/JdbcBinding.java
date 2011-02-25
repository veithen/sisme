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
package com.googlecode.sisme.jdbc.impl;

import com.googlecode.sisme.Destination;
import com.googlecode.sisme.ImportBinding;
import com.googlecode.sisme.Interface;
import com.googlecode.sisme.MessageData;
import com.googlecode.sisme.Operation;

public class JdbcBinding extends ImportBinding {
    public JdbcBinding(Interface iface) {
        super(iface);
    }

    @Override
    public boolean isCompatible(Destination destination) {
        return destination instanceof JdbcDestination;
    }

    @Override
    public void invoke(Operation operation, MessageData messageData, Destination destination) {
        // TODO Auto-generated method stub
        
    }
}
