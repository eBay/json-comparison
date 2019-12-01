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
import java.util.stream.Stream;

import com.json.comparison.comprator.model.api.FieldComparison;

/**
 * A {@link JsonComparisonResultImpl} class is the implementation Json Comparison Result
 * @See JsonComparisonResult
 */
public class JsonComparisonResultImpl implements JsonComparisonResult {

  private String actual;
  private String expected;
  private Collection<FieldComparison> modifiedFields;
  private Collection<FieldComparison> missingFields;
  private Collection<FieldComparison> newFields;

  private JsonComparisonResultImpl(JsonComparisonResultImplBuilder jsonComparisonResultImplBuilder) {
    this.actual = jsonComparisonResultImplBuilder.actual;
    this.expected = jsonComparisonResultImplBuilder.expected;
    this.modifiedFields = jsonComparisonResultImplBuilder.modifiedFields;
    this.missingFields = jsonComparisonResultImplBuilder.missingFields;
    this.newFields = jsonComparisonResultImplBuilder.newFields;
  }

  /**
   * Use builder to create a result
   * @return
   */
  public static JsonComparisonResultImplBuilder builder() {
    return new JsonComparisonResultImplBuilder();
  }

  @Override
  public String getActual() {
    return actual;
  }

  @Override
  public String getExpected() {
    return expected;
  }

  @Override
  public boolean isMatch() {
    return !Stream.of(modifiedFields, missingFields, newFields).flatMap(Collection::stream).findAny().isPresent();
  }

  @Override
  public Collection<FieldComparison> getModifiedFields() {
    return modifiedFields;
  }

  @Override
  public Collection<FieldComparison> getMissingFields() {
    return missingFields;
  }

  @Override
  public Collection<FieldComparison> getNewFields() {
    return newFields;
  }

  /**
   * JsonComparisonResultImpl Builder
   */
  public static final class JsonComparisonResultImplBuilder {

    private String actual;
    private String expected;
    private Collection<FieldComparison> modifiedFields;
    private Collection<FieldComparison> missingFields;
    private Collection<FieldComparison> newFields;

    private JsonComparisonResultImplBuilder() {
    }

    public JsonComparisonResultImplBuilder actual(String actual) {
      this.actual = actual;
      return this;
    }

    public JsonComparisonResultImplBuilder expected(String expected) {
      this.expected = expected;
      return this;
    }

    public JsonComparisonResultImplBuilder modifiedFields(Collection<FieldComparison> modifiedFields) {
      this.modifiedFields = modifiedFields;
      return this;
    }

    public JsonComparisonResultImplBuilder missingFields(Collection<FieldComparison> missingFields) {
      this.missingFields = missingFields;
      return this;
    }

    public JsonComparisonResultImplBuilder newFields(Collection<FieldComparison> newFields) {
      this.newFields = newFields;
      return this;
    }

    public JsonComparisonResultImpl build() {
      return new JsonComparisonResultImpl(this);
    }
  }
}
