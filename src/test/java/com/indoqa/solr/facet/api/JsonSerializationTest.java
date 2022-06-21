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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

public class JsonSerializationTest {

    @Test
    public void test() throws IOException {
        FacetList facetList = new FacetList();
        TermsFacet termsFacet = new TermsFacet("abc", "abc");
        facetList.addSubFacet(termsFacet);

        this.validate(facetList.toJsonString(), Paths.get("./src/test/resources/facet-terms.json"));
    }

    @Test
    public void testDateRangeFacet() throws IOException {
        Date startDate = Date.from(LocalDate.of(2022, 1, 1).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant());
        Date endDate = Date.from(LocalDate.of(2022, 1, 2).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant());

        FacetList facetList = new FacetList();
        RangeFacet rangeFacet = RangeFacet.fromDates("name", "field", startDate, endDate, GapUnit.MINUTES, 1);
        facetList.addSubFacet(rangeFacet);

        this.validate(facetList.toJsonString(), Paths.get("./src/test/resources/facet-date-range.json"));
    }

    @Test
    public void testDomain() throws IOException {
        FacetList facetList = new FacetList();

        TermsFacet facet = new TermsFacet("name", "field");
        facet.setLimit(5);

        Domain domain = new Domain();
        domain.setExcludeTags("exclude1", "exclude2");
        facet.setDomain(domain);

        facetList.addSubFacet(facet);

        this.validate(facetList.toJsonString(), Paths.get("./src/test/resources/facet-domain.json"));
    }

    private void validate(String actual, Path path) throws IOException {
        if (Files.notExists(path)) {
            Files.write(path, actual.getBytes(StandardCharsets.UTF_8));
        }

        String expected = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        assertEquals(expected, actual);
    }
}
