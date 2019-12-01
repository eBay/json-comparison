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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.json.comparison.comprator.model.Paths;
import com.json.comparison.comprator.model.api.JsonComparatorResult;
import com.json.comparison.converter.JsonComparatorConverter;

@RunWith(MockitoJUnitRunner.class)
public class DefaultJsonCompareTest {

  private DefaultJsonComparator defaultJsonComparator;

  @Mock
  private JsonComparatorConverter jsonComparatorConverter;

  @Before
  public void setUp() {
    defaultJsonComparator = new DefaultJsonComparator(jsonComparatorConverter);
  }

  @Test
  public void compare() {

    JsonComparatorResult jsonComparatorResult = mock(JsonComparatorResult.class);
    when(jsonComparatorConverter.apply(any())).thenReturn(jsonComparatorResult);
    JsonComparatorResult actualResult = defaultJsonComparator.compare("{}", "{}", Paths.createRegexPaths());

    assertThat(jsonComparatorResult, equalTo(actualResult));

    verify(jsonComparatorConverter).apply(any());
  }
}