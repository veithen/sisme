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
package com.googlecode.sisme.help;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.googlecode.sisme.framework.FrameworkSchemaProvider;
import com.googlecode.sisme.framework.FrameworkSchemaProviderException;

@SuppressWarnings("serial")
public class SchemaServlet extends HttpServlet {
    private final ServiceTracker tracker;
    
    public SchemaServlet(ServiceTracker tracker) {
        this.tracker = tracker;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() == 0 || pathInfo.equals("/")) {
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            for (ServiceReference reference : tracker.getServiceReferences()) {
                out.println(reference.getProperty(FrameworkSchemaProvider.P_NAMESPACE) + " " + reference.getProperty(FrameworkSchemaProvider.P_FILENAME));
            }
        } else {
            String requestedFilename;
            if (pathInfo.startsWith("/")) {
                requestedFilename = pathInfo.substring(1);
            } else {
                requestedFilename = pathInfo;
            }
            ImportResolverImpl importResolver = new ImportResolverImpl();
            FrameworkSchemaProvider schemaProvider = null;
            for (ServiceReference reference : tracker.getServiceReferences()) {
                String namespaceUri = (String)reference.getProperty(FrameworkSchemaProvider.P_NAMESPACE);
            	String filename = (String)reference.getProperty(FrameworkSchemaProvider.P_FILENAME);
            	importResolver.addLocation(namespaceUri, filename);
                if (filename.equals(requestedFilename)) {
                    schemaProvider = (FrameworkSchemaProvider)tracker.getService(reference);
                }
            }
            if (schemaProvider != null) {
                try {
                    SAXTransformerFactory factory = (SAXTransformerFactory)TransformerFactory.newInstance();
                    TransformerHandler transformerHandler = factory.newTransformerHandler();
                    transformerHandler.setResult(new StreamResult(response.getOutputStream()));
                    factory.newTransformer().transform(new DOMSource(schemaProvider.getSchema(importResolver)),
                            new SAXResult(new SchemaPrettyPrinter(transformerHandler)));
                } catch (TransformerException ex) {
                    throw new ServletException(ex);
                } catch (FrameworkSchemaProviderException ex) {
                    throw new ServletException(ex);
                }
            } else {
                // TODO
            }
        }
    }
}
