/************************************************************************
 Copyright 2019 eBay Inc.
 Author/Developer(s): Ahmad Mahagna

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **************************************************************************/
package com.json.comparison;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.json.comparison.comprator.DefaultJsonComparator;
import com.json.comparison.comprator.JsonComparator;
import com.json.comparison.comprator.model.Paths;
import com.json.comparison.comprator.model.api.JsonComparatorResult;
import com.json.comparison.converter.JsonComparatorConverter;
import com.json.comparison.filter.ComparatorFilter;

/**
 * A JsonCompare class represents the main class of the project that can take two Json and return all differences
 *
 * Basic Usage no filters return all differences in the result
 *
 * JsonComparisonResult compare = JsonCompare.builder()
 *         .build()
 *         .compare(expectedJson, actualJson);
 *
 *
 * Usage with filters and ignore order
 *
 * JsonComparisonResult compare = JsonCompare.builder()
 *         .addFilter(new IgnoredPathFilter(Paths.createRegexPaths(ignorePath)))
 *         .ignoreOrder(Paths.createRegexPaths(ignoredOrder))
 *         .build()
 *         .compare(expectedJson, actualJson);
 *
 */
public class JsonCompare {

  private final JsonComparator jsonComparator;
  private final Paths ignoreOrder;
  private final List<ComparatorFilter> filters;

  private JsonCompare(JsonCompareBuilder builder) {
    jsonComparator = builder.jsonComparator;
    ignoreOrder = builder.ignoreOrder;
    filters = builder.filters;
  }

  public static JsonCompareBuilder builder() {
    return new JsonCompareBuilder();
  }

  public JsonComparisonResult compare(String expected, String actual) {

    JsonComparatorResult result = filters.stream()
        .reduce(jsonComparator.compare(expected, actual, ignoreOrder), (res, comparatorFilter) -> comparatorFilter.filter(res),
            (filterBefore, filterAfter) -> filterAfter);
    return buildResult(actual, expected, result);
  }

  private JsonComparisonResult buildResult(String actual, String expected,
      JsonComparatorResult jsonComparatorResult) {

    return JsonComparisonResultImpl.builder()
        .actual(actual)
        .expected(expected)
        .modifiedFields(jsonComparatorResult.getModifiedFields())
        .missingFields(jsonComparatorResult.getMissingFields())
        .newFields(jsonComparatorResult.getNewFields())
        .build();

  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("jsonComparator", jsonComparator)
        .append("ignoreOrder", ignoreOrder)
        .append("filters", filters)
        .toString();
  }

  public static final class JsonCompareBuilder {

    private JsonComparator jsonComparator = new DefaultJsonComparator(new JsonComparatorConverter());
    private Paths ignoreOrder = Paths.createRegexPaths();
    private List<ComparatorFilter> filters = new ArrayList<>();

    private JsonCompareBuilder() {

    }

    public JsonCompareBuilder jsonComparator(JsonComparator jsonComparator) {
      this.jsonComparator = jsonComparator;
      return this;
    }

    public JsonCompareBuilder ignoreOrder(Paths ignoreOrder) {
      this.ignoreOrder = ignoreOrder;
      return this;
    }

    public JsonCompareBuilder addFilter(ComparatorFilter filter) {
      this.filters.add(filter);
      return this;
    }

    public JsonCompare build() {
      return new JsonCompare(this);
    }
  }
}
