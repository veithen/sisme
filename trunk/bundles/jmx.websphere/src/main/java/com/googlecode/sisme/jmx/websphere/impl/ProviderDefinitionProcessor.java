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
package com.googlecode.sisme.jmx.websphere.impl;

import java.io.File;

import com.googlecode.sisme.framework.ProcessorException;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessor;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessorContext;
import com.googlecode.sisme.jmx.JMXProvider;
import com.googlecode.sisme.jmx.websphere.model.ProviderModel;

public class ProviderDefinitionProcessor extends JAXBDefinitionProcessor<ProviderModel> {
    public ProviderDefinitionProcessor() {
        super(ProviderModel.class);
    }

    @Override
    protected void parse(JAXBDefinitionProcessorContext context, ProviderModel model) throws ProcessorException {
        File wasHome = new File(model.getWasHome()).getAbsoluteFile();
        if (wasHome.exists() && wasHome.isDirectory()) {
            context.addService(JMXProvider.class.getName(), new WebSphereJMXProvider(wasHome));
        } else {
            throw new ProcessorException(wasHome + " doesn't exist or is not a directory");
        }
    }
}
