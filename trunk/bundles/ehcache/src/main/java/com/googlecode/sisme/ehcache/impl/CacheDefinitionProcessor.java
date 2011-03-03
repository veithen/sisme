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

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

import com.googlecode.sisme.ehcache.model.CacheModel;
import com.googlecode.sisme.framework.ProcessorException;
import com.googlecode.sisme.framework.component.Dependency;
import com.googlecode.sisme.framework.component.ManagedComponentFactory;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessor;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionProcessorContext;

public class CacheDefinitionProcessor extends JAXBDefinitionProcessor<CacheModel> {
    public CacheDefinitionProcessor() {
        super(CacheModel.class);
    }
    
    @Override
    protected void parse(JAXBDefinitionProcessorContext context, CacheModel model) throws ProcessorException {
        Dependency<CacheManager> cacheManager = context.createDependency(CacheManager.class, model.getManager());
        CacheConfiguration configuration = new CacheConfiguration();
        // TODO: this is not correct; we need to compose a name that also includes the namespace URI
        configuration.setName(model.getName());
        configuration.setEternal(model.isEternal());
        configuration.setMaxElementsInMemory(model.getMaxElementsInMemory());
        configuration.setOverflowToDisk(model.isOverflowToDisk());
        if (model.getMaxElementsOnDisk() != null) {
            configuration.setMaxElementsOnDisk(model.getMaxElementsOnDisk());
        }
        context.addService(ManagedComponentFactory.class.getName(), new CacheFactory(cacheManager, configuration));
    }
}
