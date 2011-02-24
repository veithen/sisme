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

/**
 * Provides administrative access to an available Derby database.
 * 
 * @author Andreas Veithen
 */
public interface Database {
    String P_NAME = "name";
    
    /**
     * The database has not been created yet.
     */
    int STATUS_NOT_CREATED = 0;
    
    /**
     * The database exists but is not online.
     */
    int STATUS_OFFLINE = 1;
    
    /**
     * The database exists and is online.
     */
    int STATUS_ONLINE = 2;
    
    /**
     * The database has just been created and is online.
     */
    int STATUS_NEW = 3;
    
    /**
     * The database is in use.
     */
    int STATUS_INUSE = 4;
    
    String getName();
    
    int getStatus();
}
