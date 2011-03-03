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
package com.googlecode.sisme.framework.schema;

import org.w3c.dom.Document;

public interface FrameworkSchemaProvider {
	String P_NAMESPACE = "namespace";
	
    /**
     * The property for the (suggested) filename of the schema. Note that
     * {@link #getSchema(ImportResolver)} must always make use of the {@link ImportResolver} to
     * determine the actual file name used for the schema.
     */
	String P_FILENAME = "filename";
	
    Document getSchema(ImportResolver resolver) throws FrameworkSchemaProviderException;
}
