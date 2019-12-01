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

import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import com.json.comparison.comprator.model.Paths;
import com.json.comparison.comprator.model.api.JsonComparatorResult;
import com.json.comparison.converter.JsonComparatorConverter;

/**
 * A DefaultJsonComparator class represents the engine of Json comparator
 * the default engine use skyscreamer project to compare two Jsons
 */
public class DefaultJsonComparator implements JsonComparator {

  private final JsonComparatorConverter jsonComparatorConverter;

  public DefaultJsonComparator(JsonComparatorConverter jsonComparatorConverter) {
    this.jsonComparatorConverter = jsonComparatorConverter;
  }

  @Override
  public JsonComparatorResult compare(String expected, String actual, Paths ignoreOrderPath) {
    try {
      JSONCompareResult jsonCompareResult = JSONCompare.compareJSON(expected, actual,
          new DefaultComparatorWithIgnoreOrder(JSONCompareMode.STRICT, ignoreOrderPath));
      return jsonComparatorConverter.apply(jsonCompareResult);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to compare Json ", e);
    }
  }

}
