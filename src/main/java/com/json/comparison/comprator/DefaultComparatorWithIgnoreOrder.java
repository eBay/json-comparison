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
package com.json.comparison.comprator;

import static org.skyscreamer.jsonassert.comparator.JSONCompareUtil.allJSONObjects;
import static org.skyscreamer.jsonassert.comparator.JSONCompareUtil.allSimpleValues;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;

import com.json.comparison.comprator.model.Paths;

/**
 * A {@link DefaultComparatorWithIgnoreOrder} class represents Json comparator with the ability to
 * ignore order in specific paths
 *
 * @see DefaultComparator
 */
public class DefaultComparatorWithIgnoreOrder extends DefaultComparator {

  private final List<Pattern> ignoreOrderPatterns;

  public DefaultComparatorWithIgnoreOrder(JSONCompareMode mode, Paths ignoreOrder) {
    super(mode);
    ignoreOrderPatterns = ignoreOrder.getRegexPaths()
        .stream()
        .map(Pattern::compile)
        .collect(Collectors.toList());
  }

  /**
   * Override Compare Json array in order to support ignore order feature
   *
   * @param prefix the json path
   * @param expected expected value
   * @param actual actual value
   * @param result compare results
   */
  @Override
  public void compareJSONArray(String prefix, JSONArray expected, JSONArray actual, JSONCompareResult result) throws JSONException {

    if (ignoreOrderPatterns.stream()
        .noneMatch(pattern -> pattern.matcher(prefix)
            .matches())) {

      if (expected.length() != actual.length()) {
        result.fail(prefix, expected, actual);
        return;
      } else if (expected.length() == 0) {
        return; // Nothing to compare
      }
      compareJSONArrayWithStrictOrder(prefix, expected, actual, result);

    } else if (allSimpleValues(expected)) {
      compareJSONArrayOfSimpleValues(prefix, expected, actual, result);
    } else if (allJSONObjects(expected)) {
      compareJSONArrayOfJsonObjects(prefix, expected, actual, result);
    } else {
      // An expensive last resort
      recursivelyCompareJSONArray(prefix, expected, actual, result);
    }
  }

}
