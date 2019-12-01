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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.json.comparison.comprator.model.JsonComparatorResultImpl;
import com.json.comparison.comprator.model.Paths;
import com.json.comparison.comprator.model.api.FieldComparison;
import com.json.comparison.comprator.model.api.JsonComparatorResult;
import com.json.comparison.filter.ComparatorFilter;
import com.json.comparison.filter.IgnoredMissingValuesFilter;
import com.json.comparison.filter.IgnoredModifiedValuesFilter;
import com.json.comparison.filter.IgnoredNewValuesFilter;
import com.json.comparison.filter.IgnoredPathFilter;

public class JsonCompareTest {

  @Test
  public void shouldReturnMatchWhenTwoJsonsAreSimilar() throws IOException {

    String actual = loadResourceAsString("actualFruit.json");
    String expected = loadResourceAsString("expectedFruit.json");

    JsonComparisonResult compareResult = JsonCompare.builder()
        .build()
        .compare(expected, actual);

    assertTrue(compareResult.isMatch());
  }


  @Test
  public void shouldReturnNewFieldDiffWhenActualJsonHasANewField() throws IOException {

    String actual = loadResourceAsString("newFieldsActualFruit.json");
    String expected = loadResourceAsString("expectedFruit.json");

    JsonComparisonResult compareResult = JsonCompare.builder()
        .build()
        .compare(expected, actual);

    assertFalse(compareResult.isMatch());
    assertEquals(compareResult.getNewFields().size(), 1);
    assertEquals(compareResult.getMissingFields().size(), 0);
    assertEquals(compareResult.getModifiedFields().size(), 0);
  }


  @Test
  public void shouldReturnMatchWhenIgnoredNewField() throws IOException {

    String actual = loadResourceAsString("newFieldsActualFruit.json");
    String expected = loadResourceAsString("expectedFruit.json");

    JsonComparisonResult compareResult = JsonCompare.builder()
        .addFilter(new IgnoredNewValuesFilter(Paths.createRegexPaths("type")))
        .build()
        .compare(expected, actual);

    assertTrue(compareResult.isMatch());
  }

  @Test
  public void shouldReturnMatchWhenIgnoredPath() throws IOException {

    String actual = loadResourceAsString("newFieldsActualFruit.json");
    String expected = loadResourceAsString("expectedFruit.json");

    JsonComparisonResult compareResult = JsonCompare.builder()
        .addFilter(new IgnoredPathFilter(Paths.createRegexPaths("type")))
        .build()
        .compare(expected, actual);

    assertTrue(compareResult.isMatch());
  }

  @Test
  public void shouldNotMatchWhenOrderIsDifferent() throws IOException {

    String actual = loadResourceAsString("orderActualFruit.json");
    String expected = loadResourceAsString("orderExpected.json");

    JsonComparisonResult compareResult = JsonCompare.builder()
        .build()
        .compare(expected, actual);

    assertFalse(compareResult.isMatch());
    assertEquals(compareResult.getNewFields().size(), 0);
    assertEquals(compareResult.getMissingFields().size(), 0);
    assertEquals(compareResult.getModifiedFields().size(), 2);
  }


  @Test
  public void shouldReturnMatchWhenIgnoredOrder() throws IOException {

    String actual = loadResourceAsString("orderActualFruit.json");
    String expected = loadResourceAsString("orderExpected.json");

    JsonComparisonResult compareResult = JsonCompare.builder()
        .ignoreOrder(Paths.createRegexPaths("color"))
        .build()
        .compare(expected, actual);

    assertTrue(compareResult.isMatch());
  }

  @Test
  public void shouldReturnMatchWhenUseCustomFilter() throws IOException {

    String actual = loadResourceAsString("customFieldsActualFruit.json");
    String expected = loadResourceAsString("expectedFruit.json");

    JsonComparisonResult compareResult = JsonCompare.builder()
        .addFilter(createCustomModifiedColorFilter())
        .build()
        .compare(expected, actual);

    assertTrue(compareResult.isMatch());
  }

  @Test
  public void shouldNotMatchWhenActualAndExpectedAreDifferent() throws IOException {

    String actual = loadResourceAsString("complexActualFruit.json");
    String expected = loadResourceAsString("complexExpected.json");

    JsonComparisonResult compareResult = JsonCompare.builder()
        .build()
        .compare(expected, actual);

    assertFalse(compareResult.isMatch());
    assertEquals(compareResult.getNewFields().size(), 1);
    assertEquals(compareResult.getMissingFields().size(), 1);
    assertEquals(compareResult.getModifiedFields().size(), 3);
  }


  @Test
  public void shouldMatchWhenFiltersMatchAllDifferences() throws IOException {

    String actual = loadResourceAsString("complexActualFruit.json");
    String expected = loadResourceAsString("complexExpected.json");

    IgnoredNewValuesFilter ignoreNewValuesFilter = new IgnoredNewValuesFilter(Paths.createRegexPaths("type"));
    IgnoredMissingValuesFilter ignoredMissingValuesFilter = new IgnoredMissingValuesFilter(Paths.createRegexPaths("size"));
    IgnoredModifiedValuesFilter ignoredModifiedValuesFilter = new IgnoredModifiedValuesFilter(Paths.createRegexPaths("origin\\.country\\.us"));

    JsonComparisonResult compareResult = JsonCompare.builder()
        .addFilter(ignoreNewValuesFilter)
        .addFilter(ignoredMissingValuesFilter)
        .addFilter(ignoredModifiedValuesFilter)
        .ignoreOrder(Paths.createRegexPaths("color"))
        .build()
        .compare(expected, actual);

    assertTrue(compareResult.isMatch());
  }


  private ComparatorFilter createCustomModifiedColorFilter() {

    return new ComparatorFilter(Paths.createRegexPaths()) {

      @Override
      public JsonComparatorResult filter(JsonComparatorResult jsonComparatorResult) {

        List<FieldComparison> modifiedFields = jsonComparatorResult.getModifiedFields()
            .stream()
            .filter(field -> !"color".equals(field.getField()))  // ignore color attribute
            .collect(Collectors.toList());

        return JsonComparatorResultImpl.builder()
            .modifiedFields(modifiedFields)
            .missingFields(jsonComparatorResult.getMissingFields())
            .newFields(jsonComparatorResult.getNewFields())
            .build();
      }
    };
  }

  private String loadResourceAsString(String resource) throws IOException {
    URL url = Resources.getResource(resource);
    return Resources.toString(url, Charsets.UTF_8);
  }
}
