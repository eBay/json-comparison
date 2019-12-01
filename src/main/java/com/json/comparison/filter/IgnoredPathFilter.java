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
package com.json.comparison.filter;

import java.util.List;
import java.util.stream.Collectors;

import com.json.comparison.comprator.model.JsonComparatorResultImpl;
import com.json.comparison.comprator.model.Paths;
import com.json.comparison.comprator.model.api.FieldComparison;
import com.json.comparison.comprator.model.api.JsonComparatorResult;

/**
 * A IgnoredPathFilter class represents filter that remove all difference types <bold>missing, new or modified values</bold> in comparator result that match provided paths
 */
public class IgnoredPathFilter extends ComparatorFilter {

  public IgnoredPathFilter(Paths ignoredPaths) {
    super(ignoredPaths);
  }

  /**
   * Filter all the 'missing, new or modified values' in the given result that match the provided paths
   * @See ComparatorFilter
   * Filter new fields that match ignored paths from result
   */
  @Override
  public JsonComparatorResult filter(JsonComparatorResult jsonComparatorResult) {

    return JsonComparatorResultImpl.builder()
        .modifiedFields(createModifiedFieldsAfterIgnoredValues(jsonComparatorResult))
        .missingFields(createMissingFieldAfterIgnoredValues(jsonComparatorResult))
        .newFields(createNewFieldAfterIgnoredValues(jsonComparatorResult))
        .build();
  }

  /**
   * Filter all modified fields that match the ignored paths
   * @param jsonComparatorResult
   * @return all fieldComparisons exclude match one
   */
  private List<FieldComparison> createModifiedFieldsAfterIgnoredValues(JsonComparatorResult jsonComparatorResult) {
    return jsonComparatorResult.getModifiedFields()
        .stream()
        .filter(failure -> !matchModifiedField(failure))
        .collect(Collectors.toList());
  }

  /**
   * Filter all missing fields that match the ignored paths
   * @param jsonComparatorResult
   * @return all fieldComparisons exclude match ones
   */
  private List<FieldComparison> createMissingFieldAfterIgnoredValues(JsonComparatorResult jsonComparatorResult) {
   return jsonComparatorResult.getMissingFields()
        .stream()
        .filter(failure -> !isMatch(concatFullFieldPath(failure.getField(),failure.getExpected().toString())))
        .collect(Collectors.toList());
  }

  /**
   * Filter all new fields that match the ignored paths
   * @param jsonComparatorResult
   * @return all fieldComparisons exclude match ones
   */
  private List<FieldComparison> createNewFieldAfterIgnoredValues(JsonComparatorResult jsonComparatorResult) {
    return jsonComparatorResult.getNewFields()
        .stream()
        .filter(failure -> !isMatch(concatFullFieldPath(failure.getField(),failure.getActual().toString())))
        .collect(Collectors.toList());
  }

  /**
   * check if modified field actual or expected value match one of ignored patterns
   * @param fieldComparison
   * @return true if field is match otherwise false
   */
  private boolean matchModifiedField(FieldComparison fieldComparison) {
    return isMatch(concatFullFieldPath(fieldComparison.getField(), fieldComparison.getExpected().toString()))
        || isMatch(concatFullFieldPath(fieldComparison.getField(), fieldComparison.getActual().toString()));

  }

}
