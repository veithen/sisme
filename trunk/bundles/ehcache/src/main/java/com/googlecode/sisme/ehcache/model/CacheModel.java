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
package com.googlecode.sisme.ehcache.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.googlecode.sisme.framework.jaxb2.model.ComponentModel;
import com.googlecode.sisme.framework.jaxb2.model.ComponentRefModel;

@XmlRootElement(name="cache")
@XmlType(name="")
public class CacheModel extends ComponentModel {
    private ComponentRefModel manager;
    private boolean eternal;
    private int maxElementsInMemory;
    private Integer maxElementsOnDisk;
    private boolean overflowToDisk;

    public ComponentRefModel getManager() {
        return manager;
    }

    public void setManager(ComponentRefModel manager) {
        this.manager = manager;
    }

    @XmlAttribute(required=true)
    public boolean isEternal() {
        return eternal;
    }

    public void setEternal(boolean eternal) {
        this.eternal = eternal;
    }

    @XmlAttribute(required=true)
    public int getMaxElementsInMemory() {
        return maxElementsInMemory;
    }

    public void setMaxElementsInMemory(int maxElementsInMemory) {
        this.maxElementsInMemory = maxElementsInMemory;
    }

    @XmlAttribute(required=true)
    public boolean isOverflowToDisk() {
        return overflowToDisk;
    }

    public void setOverflowToDisk(boolean overflowToDisk) {
        this.overflowToDisk = overflowToDisk;
    }

    @XmlAttribute
    public Integer getMaxElementsOnDisk() {
        return maxElementsOnDisk;
    }

    public void setMaxElementsOnDisk(Integer maxElementsOnDisk) {
        this.maxElementsOnDisk = maxElementsOnDisk;
    }
    
//    <xs:attribute name="diskExpiryThreadIntervalSeconds" type="xs:integer" use="optional"/> 
//    <xs:attribute name="diskSpoolBufferSizeMB" type="xs:integer" use="optional"/> 
//    <xs:attribute name="diskPersistent" type="xs:boolean" use="optional"/> 
//    <xs:attribute name="diskAccessStripes" type="xs:integer" use="optional" default="1"/> 
//    <xs:attribute name="memoryStoreEvictionPolicy" type="xs:string" use="optional"/> 
//    <xs:attribute name="clearOnFlush" type="xs:boolean" use="optional"/> 
//    <xs:attribute name="timeToIdleSeconds" type="xs:integer" use="optional"/> 
//    <xs:attribute name="timeToLiveSeconds" type="xs:integer" use="optional"/> 
//    <xs:attribute name="transactionalMode" type="transactionalMode" use="optional" default="off" /> 
//    <xs:attribute name="statistics" type="xs:boolean" use="optional" default="false"/> 
//    <xs:attribute name="copyOnRead" type="xs:boolean" use="optional" default="false"/> 
//    <xs:attribute name="copyOnWrite" type="xs:boolean" use="optional" default="false"/> 
//    <xs:attribute name="logging" type="xs:boolean" use="optional" default="false"/> 
//    <xs:attribute name="cacheLoaderTimeoutMillis" type="xs:integer" use="optional" default="0"/> 
//    <xs:attribute name="overflowToOffHeap" type="xs:boolean" use="optional" default="false"/> 
//    <xs:attribute name="maxMemoryOffHeap" type="xs:string" use="optional"/> 
}
