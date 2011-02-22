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
package com.googlecode.sisme.framework.parser;

import org.osgi.framework.Filter;
import org.osgi.util.tracker.ServiceTracker;

class DependencyImpl<T> extends ServiceTracker implements Dependency<T> {
    private final Class<T> clazz;
    private final Filter filter;
    private final Binder binder;

    public DependencyImpl(Class<T> clazz, Filter filter, Binder binder) {
        super(binder.getBundleContext(), filter, null);
        this.clazz = clazz;
        this.filter = filter;
        this.binder = binder;
    }

    public T get() {
        // TODO Auto-generated method stub
        return null;
    }
}
