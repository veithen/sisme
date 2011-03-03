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
import net.sf.ehcache.config.Configuration;

import com.googlecode.sisme.framework.component.ManagedComponentFactory;

public class CacheManagerFactory implements ManagedComponentFactory<CacheManager> {
    private final Configuration configuration;

    public CacheManagerFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public CacheManager create() {
        return new CacheManager(configuration);
    }

    public void destroy(CacheManager cacheManager) {
        cacheManager.shutdown();
    }
}
