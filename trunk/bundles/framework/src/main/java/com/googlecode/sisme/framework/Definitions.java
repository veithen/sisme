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
package com.googlecode.sisme.framework;

import javax.xml.transform.Source;

/**
 * A reference to a SiSME definitions file. Instances of this class are created and registered into
 * the OSGi runtime by deployers, from static resources or definition files retrieved from a remote
 * location. The SiSME framework listens for new {@link Definitions} being registered, automatically
 * parses them and registers a set of {@link Definition} objects for the individual definitions
 * found in the file.
 * 
 * @author Andreas Veithen
 */
public interface Definitions {
    /**
     * Get the source of the definitions file.
     * 
     * @return the source of the definitions file
     */
    Source getSource();
}
