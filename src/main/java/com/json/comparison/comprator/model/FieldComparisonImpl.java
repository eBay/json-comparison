/************************************************************************
 Copyright 2019 eBay Inc.
 Author/Developer(s): Ahmed Mahagna

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

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.json.comparison.comprator.model.api.FieldComparison;

/**
 * A FieldComparisonImpl class represents the default field comparison implementation.
 *
 * @See FieldComparison
 */
public class FieldComparisonImpl implements FieldComparison {

  private String field;
  private Object expected;
  private Object actual;

  /**
   * private full argument constructor, use b<code>FieldComparisonImpl.builder()</code> to create object
   */
  private FieldComparisonImpl(FieldComparisonImplBuilder fieldComparisonImplBuilder) {
    this.field = fieldComparisonImplBuilder.field;
    this.expected = fieldComparisonImplBuilder.expected;
    this.actual = fieldComparisonImplBuilder.actual;
  }

  /**
   * create empty Builder
   *
   * @return FieldComparisonFailureImplBuilder empty builder
   */
  public static FieldComparisonImplBuilder builder() {
    return new FieldComparisonImplBuilder();
  }

  @Override
  public String getField() {
    return field;
  }

  @Override
  public Object getExpected() {
    return expected;
  }

  @Override
  public Object getActual() {
    return actual;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FieldComparisonImpl that = (FieldComparisonImpl) o;
    return Objects.equals(field, that.field) && Objects.equals(expected, that.expected) && Objects.equals(actual, that.actual);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, expected, actual);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("field", field)
        .append("expected", expected)
        .append("actual", actual)
        .toString();
  }

  /**
   * FieldComparisonImplBuilder class represents a builder for FieldComparisonImpl
   */
  public static final class FieldComparisonImplBuilder {

    private String field;
    private Object expected;
    private Object actual;

    private FieldComparisonImplBuilder() {
      /** see <code>FieldComparisonImpl.builder()</code>*/
    }

    public FieldComparisonImplBuilder field(String field) {
      this.field = field;
      return this;
    }

    public FieldComparisonImplBuilder expected(Object expected) {
      this.expected = expected;
      return this;
    }

    public FieldComparisonImplBuilder actual(Object actual) {
      this.actual = actual;
      return this;
    }

    public FieldComparisonImpl build() {
      return new FieldComparisonImpl(this);
    }
  }

}
