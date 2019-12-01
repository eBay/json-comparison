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

/**
 * <p>
 * A FieldComparison interface represents field comparison results,
 * it provides result data of field comparison in order to get
 * the field path, expected and actual value.
 * Both expected and actual values are relevant fields when value exists in both sides of comparison
 * However when value is missing the actual value is null, vice versa when value is new the expected value is null
 * </p>
 */
public interface FieldComparison {

  /**
   * Get a field path
   *
   * @return string represents a field path
   */
  String getField();

  /**
   * Get an expected difference value
   *
   * @return object represents an expected value
   */
  Object getExpected();

  /**
   * Get an actual difference value
   *
   * @return object represents an actual value
   */
  Object getActual();

}
