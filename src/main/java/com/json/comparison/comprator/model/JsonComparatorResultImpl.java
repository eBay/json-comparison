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
package com.json.comparison.comprator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.json.comparison.comprator.model.api.FieldComparison;
import com.json.comparison.comprator.model.api.JsonComparatorResult;

/**
 * A JsonComparatorResultImpl class represents the default JsonComparatorResult implementation
 *
 * @see JsonComparatorResult
 */
public class JsonComparatorResultImpl implements JsonComparatorResult {

  private Collection<FieldComparison> modifiedFields;
  private Collection<FieldComparison> missingFields;
  private Collection<FieldComparison> newFields;

  private JsonComparatorResultImpl(JsonComparatorResultImplBuilder jsonComparatorResultImplBuilder) {
    this.modifiedFields = jsonComparatorResultImplBuilder.modifiedFields;
    this.missingFields = jsonComparatorResultImplBuilder.missingFields;
    this.newFields = jsonComparatorResultImplBuilder.newFields;
  }

  public static JsonComparatorResultImplBuilder builder() {
    return new JsonComparatorResultImplBuilder();
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonComparatorResultImpl that = (JsonComparatorResultImpl) o;
    return Objects.equals(modifiedFields, that.modifiedFields) && Objects.equals(missingFields, that.missingFields) && Objects.equals(
        newFields, that.newFields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modifiedFields, missingFields, newFields);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("modifiedFields", modifiedFields)
        .append("missingFields", missingFields)
        .append("newFields", newFields)
        .toString();
  }

  /**
   * JsonComparatorResultImplBuilder class represents a builder for JsonComparatorResultImpl
   */
  public static final class JsonComparatorResultImplBuilder {

    private Collection<FieldComparison> modifiedFields = new ArrayList<>();
    private Collection<FieldComparison> missingFields = new ArrayList<>();
    private Collection<FieldComparison> newFields = new ArrayList<>();

    private JsonComparatorResultImplBuilder() {
      /**  see <code>JsonComparatorResultImpl.builder()</code>*/
    }

    public JsonComparatorResultImplBuilder modifiedFields(Collection<FieldComparison> modifiedFields) {
      this.modifiedFields = modifiedFields;
      return this;
    }

    public JsonComparatorResultImplBuilder missingFields(Collection<FieldComparison> fieldMissing) {
      this.missingFields = fieldMissing;
      return this;
    }

    public JsonComparatorResultImplBuilder newFields(Collection<FieldComparison> newFields) {
      this.newFields = newFields;
      return this;
    }

    public JsonComparatorResultImpl build() {
      return new JsonComparatorResultImpl(this);

    }
  }
}
