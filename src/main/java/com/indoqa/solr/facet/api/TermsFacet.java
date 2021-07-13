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

import org.noggit.JSONWriter;

public class TermsFacet extends AbstractFacet {

    private static final String TYPE_TERMS = "terms";

    private static final String PARAM_SORT = "sort";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_FIELD = "field";
    private static final String PARAM_OFFSET = "offset";
    private static final String PARAM_MINCOUNT = "mincount";
    private static final String PARAM_NUM_BUCKETS = "numBuckets";
    private static final String PARAM_PREFIX = "prefix";

    private final String field;

    private Integer limit;
    private Integer offset;
    private Integer mincount;
    private boolean numBuckets;

    private String sort;
    private String prefix;

    public TermsFacet(String name, String field) {
        this(name, field, null);
    }

    public TermsFacet(String name, String field, Integer limit) {
        super(TYPE_TERMS, name);

        this.field = field;
        this.limit = limit;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public Integer getMincount() {
        return this.mincount;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setMincount(Integer mincount) {
        this.mincount = mincount;
    }

    public void setNumBuckets(boolean numBuckets) {
        this.numBuckets = numBuckets;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    protected void writeFacetConfiguration(JSONWriter jsonWriter) {
        super.writeFacetConfiguration(jsonWriter);

        writeStringField(jsonWriter, PARAM_FIELD, this.field);

        if (this.sort != null) {
            writeValueSeparator(jsonWriter);
            writeStringField(jsonWriter, PARAM_SORT, this.sort);
        }

        if (this.offset != null) {
            writeValueSeparator(jsonWriter);
            writeNumberField(jsonWriter, PARAM_OFFSET, this.offset);
        }

        if (this.limit != null) {
            writeValueSeparator(jsonWriter);
            writeNumberField(jsonWriter, PARAM_LIMIT, this.limit);
        }

        if (this.mincount != null) {
            writeValueSeparator(jsonWriter);
            writeNumberField(jsonWriter, PARAM_MINCOUNT, this.mincount);
        }

        if (this.numBuckets) {
            writeValueSeparator(jsonWriter);
            writeBooleanField(jsonWriter, PARAM_NUM_BUCKETS, this.numBuckets);
        }

        if (this.prefix != null) {
            writeValueSeparator(jsonWriter);
            writeStringField(jsonWriter, PARAM_PREFIX, this.prefix);
        }
    }
}
