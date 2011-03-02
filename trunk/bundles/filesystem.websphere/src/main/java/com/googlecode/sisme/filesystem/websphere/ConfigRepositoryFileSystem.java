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

import com.googlecode.sisme.filesystem.FileSystem;
import com.googlecode.sisme.filesystem.Path;
import com.googlecode.sisme.stream.StreamSink;
import com.googlecode.sisme.stream.StreamSource;

public class ConfigRepositoryFileSystem implements FileSystem {
    private final ConfigRepository repo;

    public ConfigRepositoryFileSystem(ConfigRepository repo) {
        this.repo = repo;
    }

    public <T> T getExtension(Class<T> extensionClass) {
        // TODO Auto-generated method stub
        return null;
    }

    public StreamSource read(Path path) {
        // TODO Auto-generated method stub
        return null;
    }

    public StreamSink write(Path path) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
