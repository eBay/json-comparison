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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.json.comparison.comprator.model.FieldComparisonImpl;
import com.json.comparison.comprator.model.JsonComparatorResultImpl;
import com.json.comparison.comprator.model.Paths;
import com.json.comparison.comprator.model.api.JsonComparatorResult;

@RunWith(MockitoJUnitRunner.class)
public class IgnoredPathFilterTest {

  private IgnoredPathFilter ignoredPathFilter;

  @Mock
  private Paths paths;

  @Before
  public void setUp() {
    when(paths.getRegexPaths()).thenReturn(Lists.newArrayList("parent\\.brand\\..*", "parent\\.path\\.a", "parent\\.path\\.2"));
    ignoredPathFilter = new IgnoredPathFilter(paths);
  }

  @Test
  public void shouldFilterPathAccordingToGivePattern_verifyResults() {

    JsonComparatorResultImpl jsonComparatorResult = JsonComparatorResultImpl.builder()
        .modifiedFields(Lists.newArrayList(
            FieldComparisonImpl.builder().field("parent.brand").actual("1").expected("2").build(),
            FieldComparisonImpl.builder().field("parent.brand2").actual("2").expected("1").build()))
        .missingFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.brand").expected("2").build()))
        .newFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.images3").actual("1").build()))
        .build();

    JsonComparatorResult actualResult = ignoredPathFilter.filter(jsonComparatorResult);
    assertThat(actualResult.getModifiedFields().size(), equalTo(1));
    assertThat(actualResult.getMissingFields().size(), equalTo(0));
    assertThat(actualResult.getNewFields().size(), equalTo(1));
  }

  @Test
  public void shouldNotFilterPathAccordingToGivePattern_verifyResults() {

    JsonComparatorResultImpl jsonComparatorResult = JsonComparatorResultImpl.builder()
        .modifiedFields(Lists.newArrayList(
            FieldComparisonImpl.builder().field("parent.brand3").actual("1").expected("2").build(),
            FieldComparisonImpl.builder().field("parent.brand2").actual("2").expected("1").build()))
        .missingFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.images2").expected("2").build()))
        .newFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.images3").actual("1").build()))
        .build();

    JsonComparatorResult actualResult = ignoredPathFilter.filter(jsonComparatorResult);
    assertThat(actualResult.getModifiedFields().size(), equalTo(2));
    assertThat(actualResult.getMissingFields().size(), equalTo(1));
    assertThat(actualResult.getNewFields().size(), equalTo(1));
  }


  @Test
  public void shouldFilterMissingFieldAccordingToGivePattern_verifyResults() {

    JsonComparatorResultImpl jsonComparatorResult = JsonComparatorResultImpl.builder()
        .missingFields(Lists.newArrayList(
                  FieldComparisonImpl.builder().field("parent.path").expected("2").build(),
                  FieldComparisonImpl.builder().field("parent.path").expected("3").build()))
        .build();

    JsonComparatorResult actualResult = ignoredPathFilter.filter(jsonComparatorResult);
    assertThat(actualResult.getMissingFields().size(), equalTo(1));
  }

  @Test
  public void shouldFilterNewFieldAccordingToGivePattern_verifyResults() {

    JsonComparatorResultImpl jsonComparatorResult = JsonComparatorResultImpl.builder()
        .newFields(Lists.newArrayList(
                  FieldComparisonImpl.builder().field("parent.path").actual("2").build(),
                  FieldComparisonImpl.builder().field("parent.path").actual("3").build()))
        .build();

    JsonComparatorResult actualResult = ignoredPathFilter.filter(jsonComparatorResult);
    assertThat(actualResult.getNewFields().size(), equalTo(1));
  }

  @Test
  public void shouldFilterModifiedFieldAccordingToGivePattern_verifyResults() {

    JsonComparatorResultImpl jsonComparatorResult = JsonComparatorResultImpl.builder()
        .modifiedFields(Lists.newArrayList(
                  FieldComparisonImpl.builder().field("parent.path").actual("1").expected("2").build(),
                  FieldComparisonImpl.builder().field("parent.path").actual("3").expected("4").build()))
        .build();

    JsonComparatorResult actualResult = ignoredPathFilter.filter(jsonComparatorResult);
    assertThat(actualResult.getModifiedFields().size(), equalTo(1));
  }
}