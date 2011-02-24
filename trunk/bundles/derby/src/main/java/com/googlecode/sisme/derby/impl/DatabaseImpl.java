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

import java.io.File;
import java.sql.SQLException;

import org.apache.derby.jdbc.EmbeddedDataSource;

import com.googlecode.sisme.derby.Database;

public class DatabaseImpl implements Database {
    public static final String ADMIN_USER = "admin";
    
    /**
     * The database has not been created yet.
     */
    public static final int STATUS_NOT_CREATED = 0;
    
    /**
     * The database exists but is not online.
     */
    public static final int STATUS_OFFLINE = 1;
    
    /**
     * The database exists and is online.
     */
    public static final int STATUS_ONLINE = 2;
    
    /**
     * The database has just been created and is online.
     */
    public static final int STATUS_NEW = 3;
    
    /**
     * The database is in use.
     */
    public static final int STATUS_INUSE = 4;
    
    private final File directory;
    private int status;

    public DatabaseImpl(File directory) {
        this.directory = directory;
        if (directory.exists()) {
            status = STATUS_OFFLINE;
        } else {
            status = STATUS_NOT_CREATED;
        }
    }

    public synchronized int getStatus() {
        return status;
    }
    
    private EmbeddedDataSource createAdminDataSource() {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName(directory.getAbsolutePath());
        ds.setUser(ADMIN_USER);
        return ds;
    }
    
    synchronized void create() throws SQLException {
        if (status != STATUS_NOT_CREATED) {
            throw new IllegalStateException();
        } else {
            EmbeddedDataSource ds = createAdminDataSource();
            ds.setCreateDatabase("create");
            // Open a connection to create the database
            ds.getConnection().close();
            status = STATUS_NEW;
        }
    }
    
    synchronized void activate() throws SQLException {
        if (status == STATUS_NOT_CREATED) {
            create();
        }
        if (status == STATUS_NEW || status == STATUS_OFFLINE) {
            createAdminDataSource().getConnection().close();
            status = STATUS_ONLINE;
        }
    }
    
    synchronized void acquire() throws SQLException {
        if (status == STATUS_INUSE) {
            throw new IllegalStateException();
        } else {
            activate();
            status = STATUS_INUSE;
            return new DatabaseHandleImpl(this);
        }
    }
    
    synchronized void release() {
        if (status != STATUS_INUSE) {
            throw new IllegalStateException();
        } else {
            status = STATUS_ONLINE;
        }
    }
}
