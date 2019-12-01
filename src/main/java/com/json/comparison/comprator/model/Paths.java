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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A Paths class represents a container contains regex json field paths,
 * this class can be used to exclude paths from comparison results or ignore order.
 * backslash required for escape character see https://www.vogella.com/tutorials/JavaRegularExpressions/article.html#backslashes-in-java
 * e.g.
 *
 * 1. Path for secondElement
 * {
 *    "rootElement": {
 *       "secondElement" : "value"
 *    }
 * }
 *
 *  is <bold>"rootElement\\.secondElement</bold>
 *
 *
 * 2. Path for first item in array
 * {
 *    "rootElement": {
 *       "array": [
 *          {
 *             "item": "value"
 *          },
 *          {
 *             "item": "value2"
 *          }
 *       ]
 *    }
 * }
 *
 * is <bold>"rootElement\\.array\\[1\\]\\.item"</bold>
 *
 *
 * 3. Path for all elements start with A
 *
 * {
 *    "rootElement": {
 *       "startWithA1": {
 *          "item": "value"
 *       },
 *       "startWithA2": {
 *          "item": "value"
 *       }
 *    }
 * }
 *
 * is <bold>"rootElement\\.startWithA.*</bold>
 *
 */
public class Paths {

  private List<String> regexPaths;

  private Paths(List<String> regexPaths) {
    this.regexPaths = regexPaths;
  }

  /**
   * Create Regex Paths from given paths
   * @param regexPaths given paths
   * @return Paths with the given stringpaths
   */
  public static Paths createRegexPaths(String... regexPaths) {
    return new Paths(Arrays.asList(regexPaths));
  }

  public List<String> getRegexPaths() {
    return regexPaths;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Paths that = (Paths) o;
    return Objects.equals(regexPaths, that.regexPaths);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("paths", regexPaths)
        .toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(regexPaths);
  }

}
