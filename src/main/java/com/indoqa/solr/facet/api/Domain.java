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

public class Domain implements JsonStreamer {

    private static final String FIELD_DOMAIN = "domain";
    private static final String FIELD_EXCLUDE_TAGS = "excludeTags";
    private static final String FIELD_BLOCK_CHILDREN = "blockChildren";
    private static final String FIELD_BLOCK_PARENT = "blockParent";

    private String[] excludeTags;

    private String blockChildren;
    private String blockParent;

    public static Domain withExcludeTags(String... excludeTags) {
        Domain result = new Domain();
        result.setExcludeTags(excludeTags);
        return result;
    }

    public String getBlockChildren() {
        return this.blockChildren;
    }

    public String getBlockParent() {
        return this.blockParent;
    }

    public String[] getExcludeTags() {
        return this.excludeTags;
    }

    public void setBlockChildren(String blockChildren) {
        this.blockChildren = blockChildren;
    }

    public void setBlockParent(String blockParent) {
        this.blockParent = blockParent;
    }

    public void setExcludeTags(String... excludeTags) {
        this.excludeTags = excludeTags;
    }

    @Override
    public void streamToJson(JSONWriter jsonWriter) {
        jsonWriter.write(FIELD_DOMAIN);
        jsonWriter.writeNameSeparator();
        jsonWriter.startObject();

        if (this.excludeTags != null) {
            jsonWriter.indent();
            AbstractFacet.writeArray(jsonWriter, FIELD_EXCLUDE_TAGS, this.excludeTags);
        }

        if (this.blockChildren != null) {
            jsonWriter.indent();
            AbstractFacet.writeStringField(jsonWriter, FIELD_BLOCK_CHILDREN, this.blockChildren);
        }

        if (this.blockParent != null) {
            jsonWriter.indent();
            AbstractFacet.writeStringField(jsonWriter, FIELD_BLOCK_PARENT, this.blockParent);
        }

        jsonWriter.indent();
        jsonWriter.endObject();
    }
}
