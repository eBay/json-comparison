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

import com.json.comparison.comprator.model.Paths;
import com.json.comparison.comprator.model.api.JsonComparatorResult;

/**
 * A JsonComparator interface represents the engine of comparing two Jsons with the ability to ignore order paths
 * @See com.ebay.catalogs.json.comparison.comprator.JsonComparator#compare(java.lang.String, java.lang.String, com.ebay.catalogs.json.comparison.comprator.model.Paths)
 */
public interface JsonComparator {

  /**
   * Compare two jsons with ability to ignore order path
   * @param expected expected Json
   * @param actual actual Json
   * @param ignoreOrderPath ignore order path
   * @return Json results @see {@link JsonComparatorResult}
   */
  JsonComparatorResult compare(String expected, String actual, Paths ignoreOrderPath);

}
