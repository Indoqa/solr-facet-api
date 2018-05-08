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

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.noggit.JSONWriter;

public class RangeFacet extends AbstractFacet {

    private static final String PLUS = "+";
    private static final String TYPE_RANGE = "range";
    private static final String PARAM_GAP = "gap";
    private static final String PARAM_END = "end";
    private static final String PARAM_START = "start";
    private static final String PARAM_FIELD = "field";

    private final String field;
    private final String start;
    private final String end;
    private final GapUnit gapUnit;
    private final int gapValue;

    private RangeFacet(String name, String field, String start, String end, GapUnit gapUnit, int gapValue) {
        super(TYPE_RANGE, name);

        this.field = field;
        this.start = start;
        this.end = end;
        this.gapUnit = gapUnit;
        this.gapValue = gapValue;
    }

    public static RangeFacet fromDates(String name, String field, Date start, Date end, GapUnit gapUnit, int gapValue) {
        return fromInstants(name, field, start.toInstant(), end.toInstant(), gapUnit, gapValue);
    }

    public static RangeFacet fromInstants(String name, String field, Instant start, Instant end, GapUnit gapUnit, int gapValue) {
        return fromStrings(name, field, DateTimeFormatter.ISO_INSTANT.format(start), DateTimeFormatter.ISO_INSTANT.format(end),
            gapUnit, gapValue);
    }

    public static RangeFacet fromStrings(String name, String field, String start, String end, GapUnit gapUnit, int gapValue) {
        return new RangeFacet(name, field, start, end, gapUnit, gapValue);
    }

    @Override
    protected void writeFacetConfiguration(JSONWriter jsonWriter) {
        super.writeFacetConfiguration(jsonWriter);

        writeStringField(jsonWriter, PARAM_FIELD, this.field);
        writeValueSeparator(jsonWriter);
        writeStringField(jsonWriter, PARAM_START, this.start);
        writeValueSeparator(jsonWriter);
        writeStringField(jsonWriter, PARAM_END, this.end);
        writeValueSeparator(jsonWriter);
        writeStringField(jsonWriter, PARAM_GAP, this.toString(this.gapValue, this.gapUnit));
    }

    private String toString(int value, GapUnit unit) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(PLUS);
        stringBuilder.append(value);
        stringBuilder.append(unit);

        return stringBuilder.toString();
    }
}
