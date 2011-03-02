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
package com.googlecode.sisme.filesystem.websphere;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.commons.beanutils.BeanUtilsBean;

/**
 * Proxy for the <code>ConfigRepository</code> MBean.
 * 
 * @author Andreas Veithen
 */
public class ConfigRepository {
    public static final int TYPE_FILE = 1;
    public static final int TYPE_DIRECTORY = 2;
    
    private final MBeanServerConnection connection;
    private final ObjectName target;
    
    // We must be very careful here not to create a class loader leak. We must not use
    // BeanUtils, and this must be an instance attribute, so that we don't keep strong
    // references to the class loader of the JMX provider after the instance has been
    // garbage collected.
    private final BeanUtilsBean beanUtils = new BeanUtilsBean();
    
    public ConfigRepository(MBeanServerConnection connection, ObjectName target) {
        this.connection = connection;
        this.target = target;
    }

    public String[] listResourceNames(String parent, int type, int depth) throws JMException, IOException {
        return (String[])connection.invoke(target, "listResourceNames", new Object[] { parent, type, depth },
                new String[] { "java.lang.String", "int", "int" });
    }
    
    public DocumentContentSource extract(String docURI) throws JMException, IOException {
        DocumentContentSource result = new DocumentContentSource();
        try {
            beanUtils.copyProperties(result, connection.invoke(target, "extract", new Object[] { docURI },
                    new String[] { "java.lang.String" }));
        } catch (IllegalAccessException ex) {
            throw new Error(ex);
        } catch (InvocationTargetException ex) {
            throw new Error(ex);
        }
        return result;
    }
}
