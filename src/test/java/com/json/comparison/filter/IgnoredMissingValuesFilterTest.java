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
public class IgnoredMissingValuesFilterTest {

  private IgnoredMissingValuesFilter ignoredMissingValuesFilter;

  @Mock
  private Paths paths;

  @Before
  public void setUp() {
    when(paths.getRegexPaths()).thenReturn(Lists.newArrayList("parent\\.path\\.2", "parent\\.path2\\.a"));
    ignoredMissingValuesFilter = new IgnoredMissingValuesFilter(paths);
  }

  @Test
  public void shouldFilterMissingValuesAccordingToGivePattern_verifyResults() {

    JsonComparatorResultImpl jsonComparatorResult = JsonComparatorResultImpl.builder()
        .modifiedFields(Lists.newArrayList(
            FieldComparisonImpl.builder().field("parent.path").actual("2").expected("4").build(),
            FieldComparisonImpl.builder().field("parent.path2").actual("a").expected("4").build()))
        .missingFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.path").expected("2").build()))
        .newFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.path").actual("2").build()))
        .build();

    JsonComparatorResult actualResult = ignoredMissingValuesFilter.filter(jsonComparatorResult);
    assertThat(actualResult.getModifiedFields().size(), equalTo(2));
    assertThat(actualResult.getMissingFields().size(), equalTo(0));
    assertThat(actualResult.getNewFields().size(), equalTo(1));
  }

  @Test
  public void shouldNotFilterMissingValuesAccordingToGivePattern_verifyResults() {

    JsonComparatorResultImpl jsonComparatorResult = JsonComparatorResultImpl.builder()
        .modifiedFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.path").actual("b").expected("4").build()))
        .missingFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.path").expected("2").build()))
        .newFields(Lists.newArrayList(FieldComparisonImpl.builder().field("parent.path").actual("1").build()))
        .build();

    JsonComparatorResult actualResult = ignoredMissingValuesFilter.filter(jsonComparatorResult);
    assertThat(actualResult.getModifiedFields().size(), equalTo(1));
    assertThat(actualResult.getMissingFields().size(), equalTo(0));
    assertThat(actualResult.getNewFields().size(), equalTo(1));
  }

}