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
package com.googlecode.sisme.ehcache.impl;

import net.sf.ehcache.config.Configuration;

import com.googlecode.sisme.ehcache.model.CacheManagerModel;
import com.googlecode.sisme.framework.ProcessorException;
import com.googlecode.sisme.framework.component.ManagedComponentFactory;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessor;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessorContext;

public class CacheManagerDefinitionProcessor extends JAXBDefinitionProcessor<CacheManagerModel> {
    public CacheManagerDefinitionProcessor() {
        super(CacheManagerModel.class);
    }
    
    @Override
    protected void parse(JAXBDefinitionProcessorContext context, CacheManagerModel model) throws ProcessorException {
        Configuration configuration = new Configuration();
        // We don't want this thing to call home...
        configuration.setUpdateCheck(false);
        context.addService(ManagedComponentFactory.class.getName(), new CacheManagerFactory(configuration));
    }
}
