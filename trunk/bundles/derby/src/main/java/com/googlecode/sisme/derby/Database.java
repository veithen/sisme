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
package com.googlecode.sisme.derby;

import java.io.File;

public class Database {
    public static final String ADMIN_USER = "admin";
    
    /**
     * The database exists but is not online.
     */
    public static final int STATE_OFFLINE = 0;
    
    /**
     * The database exists and is online.
     */
    public static final int STATE_ONLINE = 1;
    
    /**
     * The database has just been created and is online.
     */
    public static final int STATE_NEW = 2;
    
    /**
     * The database is in use, i.e. a data source has been requested created.
     */
    public static final int STATE_INUSE = 3;
    
    private final File directory;
    private final int status;

    public Database(File directory, int status) {
        this.directory = directory;
        this.status = status;
    }
}
