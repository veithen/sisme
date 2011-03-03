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
package com.googlecode.sisme.framework.component;

/**
 * A dependency on a particular facet of a component. A component facet is an interface that
 * provides access to specific component methods.
 * 
 * @param <T>
 *            the facet class
 * 
 * @author Andreas Veithen
 */
public interface Dependency<T> {
    /**
     * Get a reference to the component facet.
     * 
     * @return the reference to the component facet
     * @throws IllegalStateException
     *             if the dependency has not been resolved yet
     */
    T get();
}
