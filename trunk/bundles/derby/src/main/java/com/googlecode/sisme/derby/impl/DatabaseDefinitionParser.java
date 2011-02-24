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
package com.googlecode.sisme.derby.impl;

import java.sql.SQLException;

import javax.xml.namespace.QName;

import org.osgi.framework.BundleContext;

import com.googlecode.sisme.derby.DataSourceFactory;
import com.googlecode.sisme.derby.model.DatabaseModel;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionParser;
import com.googlecode.sisme.framework.jaxb2.JAXBDefinitionParserContext;

public class DatabaseDefinitionParser extends JAXBDefinitionParser<DatabaseModel> {
    private final DatabaseManager manager;
    
    public DatabaseDefinitionParser(BundleContext context, DatabaseManager manager) {
        super(context, new QName("http://sisme.googlecode.com/derby", "database"), DatabaseModel.class);
        this.manager = manager;
    }

    @Override
    protected void parse(JAXBDefinitionParserContext context, DatabaseModel model) {
        // TODO: need to handle the lifecycle here
        try {
            context.addManagedObject(DataSourceFactory.class.getName(), manager.acquireDatabase(model.getName()));
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            throw new Error(ex);
        }
    }
}
