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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;

public class Buckets implements Iterable<NamedList<Object>> {

    private final List<NamedList<Object>> buckets;
    private final int numBuckets;

    public Buckets(List<NamedList<Object>> buckets, int numBuckets) {
        super();

        this.buckets = buckets;
        this.numBuckets = numBuckets;
    }

    @SuppressWarnings("unchecked")
    public static Buckets fromFacet(NamedList<Object> facet) {
        if (facet == null) {
            return new Buckets(Collections.<NamedList<Object>> emptyList(), 0);
        }

        List<NamedList<Object>> buckets = (List<NamedList<Object>>) facet.get("buckets");
        if (buckets == null) {
            return new Buckets(Collections.<NamedList<Object>> emptyList(), 0);
        }

        int numBuckets = getInt(facet, "numBuckets", -1);
        return new Buckets(buckets, numBuckets);
    }

    @SuppressWarnings("unchecked")
    public static Buckets fromResponse(QueryResponse queryResponse, String facetName) {
        NamedList<Object> facets = (NamedList<Object>) queryResponse.getResponse().get("facets");
        if (facets == null) {
            return new Buckets(Collections.<NamedList<Object>> emptyList(), 0);
        }

        NamedList<Object> facet = (NamedList<Object>) facets.get(facetName);
        return fromFacet(facet);
    }

    public static int getInt(NamedList<?> list, String name) {
        return ((Number) list.get(name)).intValue();
    }

    public static int getInt(NamedList<?> list, String name, int defaultValue) {
        Object value = list.get(name);
        if (value == null) {
            return defaultValue;
        }

        return ((Number) value).intValue();
    }

    public NamedList<Object> getBucket(int index) {
        return this.buckets.get(index);
    }

    public int getBucketCount() {
        return this.buckets.size();
    }

    public int getNumBuckets() {
        return this.numBuckets;
    }

    @Override
    public Iterator<NamedList<Object>> iterator() {
        return this.buckets.iterator();
    }

    public Stream<NamedList<Object>> stream() {
        return this.buckets.stream();
    }
}
