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
package com.json.comparison.converter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareResult;

import com.json.comparison.comprator.model.FieldComparisonImpl;
import com.json.comparison.comprator.model.JsonComparatorResultImpl;
import com.json.comparison.comprator.model.api.JsonComparatorResult;

public class JsonCompareConverterTest {

  private JsonComparatorConverter jsonComparatorConverter;

  @Before
  public void setUp() {
    jsonComparatorConverter = new JsonComparatorConverter();
  }

  @Test
  public void convertWithDifferences() {

    JSONCompareResult jsonCompareResult = new JSONCompareResult()
        .fail("fieldA", "a", "b")
        .missing("fieldB", "a")
        .unexpected("fieldC", "b");

    JsonComparatorResult actualResult = jsonComparatorConverter.apply(jsonCompareResult);
    JsonComparatorResultImpl expectedResult = JsonComparatorResultImpl.builder()
        .modifiedFields(Collections.singletonList(createFieldComparison("fieldA", "a", "b")))
        .missingFields(Collections.singletonList(createFieldComparison("fieldB", "a", null)))
        .newFields(Collections.singletonList(createFieldComparison("fieldC", null, "b")))
        .build();

    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void convertWithNoDifferences() {

    JSONCompareResult jsonCompareResult = new JSONCompareResult();

    JsonComparatorResult actualResult = jsonComparatorConverter.apply(jsonCompareResult);
    JsonComparatorResultImpl expectedResult = JsonComparatorResultImpl.builder().build();

    assertThat(actualResult, equalTo(expectedResult));
  }

  private FieldComparisonImpl createFieldComparison(String field, String expected, String actual) {
    return FieldComparisonImpl.builder()
        .field(field)
        .actual(actual)
        .expected(expected)
        .build();
  }
}