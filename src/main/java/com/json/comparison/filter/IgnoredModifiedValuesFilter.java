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
 * A IgnoredModifiedValuesFilter class represents filter that remove all the 'modified values' in comparator result that match provided paths
 */
public class IgnoredModifiedValuesFilter extends ComparatorFilter {

  public IgnoredModifiedValuesFilter(Paths ignoredPaths) {
    super(ignoredPaths);
  }

  /**
   * Filter all the 'modified values' in the given result that match the provided paths
   * @See ComparatorFilter
   * Filter modified fields that match ignored paths from result
   */
  @Override
  public JsonComparatorResult filter(JsonComparatorResult jsonComparatorResult) {

    return JsonComparatorResultImpl.builder()
        .modifiedFields(createModifiedFieldsAfterIgnoredValues(jsonComparatorResult))
        .missingFields(jsonComparatorResult.getMissingFields())
        .newFields(jsonComparatorResult.getNewFields())
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
   * check if modified field actual or expected value match one of ignored patterns
   * @param fieldComparison
   * @return true if field is match otherwise false
   */
  private boolean matchModifiedField(FieldComparison fieldComparison) {
    return isMatch(concatFullFieldPath(fieldComparison.getField(), fieldComparison.getExpected().toString()))
        || isMatch(concatFullFieldPath(fieldComparison.getField(), fieldComparison.getActual().toString()));

  }

}
