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
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_FIELD = "field";
    private static final String PARAM_NUM_BUCKETS = "numBuckets";

    private Integer limit;
    private final String field;
    private boolean numBuckets;

    public TermsFacet(String name, String field) {
        this(name, field, null);
    }

    public TermsFacet(String name, String field, Integer limit) {
        super(TYPE_TERMS, name);

        this.field = field;
        this.limit = limit;
    }

    public void setNumBuckets(boolean numBuckets) {
        this.numBuckets = numBuckets;
    }

    @Override
    protected void writeFacetConfiguration(JSONWriter jsonWriter) {
        this.writeStringField(jsonWriter, PARAM_FIELD, this.field);

        if (this.limit != null) {
            this.writeValueSeparator(jsonWriter);
            this.writeNumberField(jsonWriter, PARAM_LIMIT, this.limit);
        }

        if (this.numBuckets) {
            this.writeValueSeparator(jsonWriter);
            this.writeBooleanField(jsonWriter, PARAM_NUM_BUCKETS, this.numBuckets);
        }
    }
}
