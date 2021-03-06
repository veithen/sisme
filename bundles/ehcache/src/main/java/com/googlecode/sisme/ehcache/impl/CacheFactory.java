/*
 * Copyright 2009-2011 Andreas Veithen
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

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

import com.googlecode.sisme.framework.component.Dependency;
import com.googlecode.sisme.framework.component.ManagedComponentFactory;

public class CacheFactory implements ManagedComponentFactory<Cache> {
    private final Dependency<CacheManager> cacheManager;
    private final CacheConfiguration configuration;

    public CacheFactory(Dependency<CacheManager> cacheManager, CacheConfiguration configuration) {
        this.cacheManager = cacheManager;
        this.configuration = configuration;
    }

    public Cache create() {
        Cache cache = new Cache(configuration);
        cacheManager.get().addCache(cache);
        return cache;
    }

    public void destroy(Cache cache) {
        cacheManager.get().removeCache(cache.getName());
    }
}
