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
package com.json.comparison.comprator.model.api;

import java.util.Collection;

/**
 * <p>
 * A JsonComparatorResult interface represents a container for all comparison results split by three collections
 * </p>
 * the modified fields <code>getModifiedFields()</code>
 * the missing fields <code>getMissingFields()</code>
 * the new fields <code>getNewFields()</code>
 *
 * @see FieldComparison
 */
public interface JsonComparatorResult {

  /**
   * Get modified fields
   *
   * @return collection represents modified fields data, empty if no modified fields
   */
  Collection<FieldComparison> getModifiedFields();

  /**
   * Get missing fields
   *
   * @return collection represents missing fields data, empty if no missing fields
   */
  Collection<FieldComparison> getMissingFields();

  /**
   * Get new fields
   *
   * @return collection represents new fields data, empty if no new fields
   */
  Collection<FieldComparison> getNewFields();

}
