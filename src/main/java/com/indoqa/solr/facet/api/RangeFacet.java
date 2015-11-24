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

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.solr.common.util.DateUtil;
import org.noggit.JSONWriter;

public class RangeFacet extends AbstractFacet {

    private static final String PLUS = "+";
    private static final String TYPE_RANGE = "range";
    private static final String PARAM_GAP = "gap";
    private static final String PARAM_END = "end";
    private static final String PARAM_START = "start";
    private static final String PARAM_FIELD = "field";

    private final String field;
    private final Date start;
    private final Date end;
    private final GapUnit gapUnit;
    private final int gapValue;

    public RangeFacet(String name, String field, Date start, Date end, GapUnit gapUnit, int gapValue) {
        super(TYPE_RANGE, name);

        this.field = field;
        this.start = start;
        this.end = end;
        this.gapUnit = gapUnit;
        this.gapValue = gapValue;
    }

    @Override
    protected void writeFacetConfiguration(JSONWriter jsonWriter) {
        this.writeStringField(jsonWriter, PARAM_FIELD, this.field);
        this.writeValueSeparator(jsonWriter);
        this.writeStringField(jsonWriter, PARAM_START, this.toString(this.start));
        this.writeValueSeparator(jsonWriter);
        this.writeStringField(jsonWriter, PARAM_END, this.toString(this.end));
        this.writeValueSeparator(jsonWriter);
        this.writeStringField(jsonWriter, PARAM_GAP, this.toString(this.gapValue, this.gapUnit));
    }

    private String toString(Date date) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            DateUtil.formatDate(date, Calendar.getInstance(), stringBuilder);
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new FacetMarshallingException(e);
        }
    }

    private String toString(int value, GapUnit unit) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(PLUS);
        stringBuilder.append(value);
        stringBuilder.append(unit);

        return stringBuilder.toString();
    }
}
