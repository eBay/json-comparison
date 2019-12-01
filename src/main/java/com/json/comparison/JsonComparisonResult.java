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

import java.util.Collection;

import com.json.comparison.comprator.model.api.FieldComparison;

/**
 * A JsonComparisonResult interface represents the final result include the actual and expected Jsons
 * the <code>isMatch</code> return true if the two given Jsons are match
 * @See JsonComparatorResult
 */
public interface JsonComparisonResult {

  String getActual();

  String getExpected();

  boolean isMatch();

  Collection<FieldComparison> getModifiedFields();

  Collection<FieldComparison> getMissingFields();

  Collection<FieldComparison> getNewFields();

}
