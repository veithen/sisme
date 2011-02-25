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
package com.googlecode.sisme.core.impl;

import com.googlecode.sisme.DestinationSelector;
import com.googlecode.sisme.Import;
import com.googlecode.sisme.ImportBinding;
import com.googlecode.sisme.Operation;
import com.googlecode.sisme.RequestContext;

public class DynamicImport extends Import {
    private final DestinationSelector destinationSelector;

    public DynamicImport(ImportBinding binding, DestinationSelector destinationSelector) {
        super(binding);
        this.destinationSelector = destinationSelector;
    }

    @Override
    public final void invoke(Operation operation, RequestContext requestContext) {
        getBinding().invoke(operation, requestContext, destinationSelector.getDestination(requestContext));
    }
}
