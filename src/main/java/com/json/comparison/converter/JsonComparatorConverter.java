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

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.skyscreamer.jsonassert.JSONCompareResult;

import com.json.comparison.comprator.model.FieldComparisonImpl;
import com.json.comparison.comprator.model.JsonComparatorResultImpl;
import com.json.comparison.comprator.model.JsonComparatorResultImpl.JsonComparatorResultImplBuilder;
import com.json.comparison.comprator.model.api.FieldComparison;
import com.json.comparison.comprator.model.api.JsonComparatorResult;

/**
 * A JsonComparatorConverter class represents converter from JSONCompareResult of skyscreamer project
 * to ebay JsonComparatorResult
 */
public class JsonComparatorConverter implements Function<JSONCompareResult, JsonComparatorResult> {

  @Override
  public JsonComparatorResult apply(JSONCompareResult jsonCompareResult) {

    JsonComparatorResultImplBuilder resultJsonComparatorResultImplBuilder = JsonComparatorResultImpl.builder()
        .modifiedFields(createFieldComparison(jsonCompareResult.getFieldFailures()))
        .missingFields(createFieldComparison(jsonCompareResult.getFieldMissing()))
        .newFields(createFieldComparison(jsonCompareResult.getFieldUnexpected()));
    return resultJsonComparatorResultImplBuilder.build();
  }

  private Collection<FieldComparison> createFieldComparison(
      Collection<org.skyscreamer.jsonassert.FieldComparisonFailure> fieldComparisonFailures) {
    return fieldComparisonFailures.stream()
        .map(this::convertFieldComparison)
        .collect(Collectors.toList());
  }

  private FieldComparison convertFieldComparison(org.skyscreamer.jsonassert.FieldComparisonFailure fieldComparisonFailure) {
    return FieldComparisonImpl.builder()
        .field(fieldComparisonFailure.getField())
        .expected(fieldComparisonFailure.getExpected())
        .actual(fieldComparisonFailure.getActual())
        .build();
  }

}
