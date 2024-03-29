/*
 * Licensed to the Indoqa Software Design und Beratung GmbH (Indoqa) under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Indoqa licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.indoqa.solr.facet.api;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.noggit.CharArr;
import org.noggit.JSONWriter;

public abstract class AbstractFacet implements Facet {

    private static final String FIELD_TYPE = "type";
    private static final String FIELD_FACET = "facet";

    private String name;
    private String type;
    private Domain domain;

    protected List<Facet> subFacets = new ArrayList<>();

    public AbstractFacet(String type, String name) {
        this.type = type;
        this.name = name;
    }

    protected static void writeArray(JSONWriter jsonWriter, String fieldName, Object[] array) {
        jsonWriter.write(fieldName);
        jsonWriter.writeNameSeparator();

        if (array.length == 1) {
            jsonWriter.write(array[0]);
        } else {
            jsonWriter.write(array);
        }
    }

    protected static void writeBooleanField(JSONWriter jsonWriter, String fieldName, boolean value) {
        jsonWriter.write(fieldName);
        jsonWriter.writeNameSeparator();
        jsonWriter.write(value);
    }

    protected static void writeNumberField(JSONWriter jsonWriter, String fieldName, Number value) {
        jsonWriter.write(fieldName);
        jsonWriter.writeNameSeparator();
        jsonWriter.write(value);
    }

    protected static void writeStringField(JSONWriter jsonWriter, String fieldName, String value) {
        jsonWriter.write(fieldName);
        jsonWriter.writeNameSeparator();
        jsonWriter.write(value);
    }

    protected static void writeValueSeparator(JSONWriter jsonWriter) {
        jsonWriter.writeValueSeparator();
        jsonWriter.indent();
    }

    @Override
    public Facet addSubFacet(Facet facet) {
        this.subFacets.add(facet);
        return this;
    }

    public Domain getDomain() {
        return this.domain;
    }

    public String getName() {
        return this.name;
    }

    public int getSubFacetCount() {
        return this.subFacets.size();
    }

    public boolean hasSubFacets() {
        return !this.subFacets.isEmpty();
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public void streamToJson(JSONWriter jsonWriter) {
        jsonWriter.write(this.name);
        jsonWriter.writeNameSeparator();

        jsonWriter.startObject();
        jsonWriter.indent();
        writeStringField(jsonWriter, FIELD_TYPE, this.type);
        writeValueSeparator(jsonWriter);
        this.writeFacetConfiguration(jsonWriter);
        this.writeSubFacets(jsonWriter);
        jsonWriter.indent();

        jsonWriter.endObject();
    }

    @Override
    public String toJsonString() {
        CharArr charArr = new CharArr();
        JSONWriter jsonWriter = new JSONWriter(charArr, 2);

        jsonWriter.startObject();
        jsonWriter.indent();

        this.streamToJson(jsonWriter);

        jsonWriter.indent();
        jsonWriter.endObject();

        return charArr.toString();
    }

    protected void writeFacetConfiguration(JSONWriter jsonWriter) {
        if (this.domain != null) {
            this.domain.streamToJson(jsonWriter);
            writeValueSeparator(jsonWriter);
        }
    }

    protected void writeFacets(JSONWriter jsonWriter, List<Facet> facets) {
        for (ListIterator<Facet> it = facets.listIterator(); it.hasNext();) {
            it.next().streamToJson(jsonWriter);

            if (it.hasNext()) {
                writeValueSeparator(jsonWriter);
            }
        }
    }

    protected void writeSubFacets(JSONWriter jsonWriter) {
        if (this.subFacets.isEmpty()) {
            return;
        }

        writeValueSeparator(jsonWriter);
        jsonWriter.write(FIELD_FACET);
        jsonWriter.writeNameSeparator();

        jsonWriter.startObject();
        jsonWriter.indent();
        this.writeFacets(jsonWriter, this.subFacets);
        jsonWriter.indent();
        jsonWriter.endObject();
    }
}
