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
package com.googlecode.sisme.filesystem;

import com.googlecode.sisme.stream.StreamSink;
import com.googlecode.sisme.stream.StreamSource;

/**
 * A view on a particular file system. Depending on the type of the underlying
 * file system and how the instance was created, a {@link FileSystem} object may
 * represent one of the following things:
 * <ul>
 * <li>A login session on an authenticated remote file system or a file system
 * view bound to a particular user. In that case, all file access occurs with
 * the identity of the user specified at creation time.
 * <li>A file system view that automatically passes the identity (and
 * potentially credentials) of the invoking user, i.e. the identity bound to the
 * current thread when a method on this interface is invoked.
 * <li>A direct view on the file system if the underlying file system has no
 * concept of accessing user.
 * </ul>
 * 
 * @author Andreas Veithen
 */
public interface FileSystem {
    /**
     * Get the file system extension with a given class. This method allows file
     * systems to expose more advanced features.
     * 
     * @param <T>
     *            the extension class
     * @param extensionClass
     *            the extension class
     * @return an instance of the file system extension, or <code>null</code> if
     *         the requested extension is not supported
     */
    <T> T getExtension(Class<T> extensionClass);
    
    /**
     * Open the given file for reading.
     * 
     * @param path the path to the file
     * @return a stream source to read the file content
     */
    StreamSource read(Path path);
    
    /**
     * Open the given file for writing.
     * 
     * @param path the path to the file
     * @return a stream source to write the file content
     */
    StreamSink write(Path path);
}
