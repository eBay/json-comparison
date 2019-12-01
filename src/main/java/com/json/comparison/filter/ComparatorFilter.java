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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.json.comparison.comprator.model.Paths;
import com.json.comparison.comprator.model.api.JsonComparatorResult;

/**
 * A ComparatorFilter abstract class represents the common logic for comparator filter
 * The constructor required to provide ignored Path in order to filter result that match those paths
 *
 * expose <code>filter(JsonComparatorResult jsonComparatorResult</code> method
 * Allows for manipulation comparator result.
 *
 * @See Filters in com.ebay.catalogs.json.comparison.filter
 */

public abstract class ComparatorFilter {

  private static final String CONCAT_FIELD_FORMAT = "%s.%s";
  protected final List<Pattern> patterns;

  public ComparatorFilter(Paths ignoredPaths) {
    this.patterns = createIgnorePath(ignoredPaths);
  }

  /**
   * Filter Json comparator result
   *
   * @param jsonComparatorResult comparator result
   * @return comparator result after manipulation
   */
  public abstract JsonComparatorResult filter(JsonComparatorResult jsonComparatorResult);

  /**
   * Create Ignore regex Paths from provided Paths
   * @param ignoredPaths the given ignored path
   * @return collection of patterns
   */
  private List<Pattern> createIgnorePath(Paths ignoredPaths) {
    return ignoredPaths.getRegexPaths()
        .stream()
        .map(Pattern::compile)
        .collect(Collectors.toList());
  }

  /**
   * Check if the given field is match any pattern
   * @param fieldPath the given field path
   * @return true if the given field is match any pattern otherwise false
   */
  protected boolean isMatch(String fieldPath) {
    return patterns.stream()
        .anyMatch(pattern -> pattern.matcher(fieldPath)
            .matches());
  }

  /**
   * Concat the prefix path with field name in case of fieldPath not empty otherwise return field name
   * @param prefixFieldPath
   * @param fieldName
   * @return string represents the full path for field
   */
  protected String concatFullFieldPath(String prefixFieldPath, String fieldName) {
    return StringUtils.isNotBlank(prefixFieldPath) ? String.format(CONCAT_FIELD_FORMAT, prefixFieldPath, fieldName) : fieldName;
  }
}
