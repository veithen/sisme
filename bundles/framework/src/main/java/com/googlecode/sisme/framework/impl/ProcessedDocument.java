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
/**
 * 
 */
package com.googlecode.sisme.framework.impl;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;

import com.googlecode.sisme.framework.document.processor.DocumentProcessorContext;

public class ProcessedDocument extends AbstractProcessorContext implements DocumentProcessorContext {
    ProcessedDocument(BundleContext bundleContext) {
        super(bundleContext);
    }

    @Override
    protected void processProperties(Dictionary<String,Object> properties) {
        // TODO: add a property that links the created object(s) to the document
    }
}