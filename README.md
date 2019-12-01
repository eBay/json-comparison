# Json Comparison
General Json Comparison, but includes various features of interest.

# Summary
An advanced JSON Comparison tool that takes two strings (JSON strings) as input and outputs a diff result. The diff result includes missing fields, changed(modified) fields, and new(unexpected) fields. By default, a different order is considered a difference. 
In order to forgive re-ordering, a values or exclude path filters option can assist by providing a path (regex).

# Advantages
This project provide a full unique solution for JSON comparison including: 
* Simple difference result structure divided by type (new, missing and modified fields);
* Ability to ignore specific path;
* Ability to ignore order difference in particular path;

# How Does This Json Comparison Library Offer Value
  There is no doubt that many Java projects are dealing with Json comparison, so why do we need another project?
  
Most existing projects are <b>only</b> suitable for writing tests that compare two JSON inputs.  The existing libraries provide <b>assert</b> methods that answer a closed question 'Are the two JSON Strings Equal?' and they may throw an exception when the two inputs are different. This is ideal for test usage. However, these projects make it hard to integrate JSON comparison results into  business logic that decision makers depend on. It is also difficult for summaries and reports from comparison results.

Moreover some existing projects do not support features such as 'Ignore Paths' or 'Ignore Order.' As a result, existing tools may be useful for simple use only.

This project provides a full solution for JSON comparison. In particular if you need to integrate and manipulate comparison results into your business logic, this framework will be useful. In addition, if you are looking for comparison that includes advanced features like 'Ignore Paths' and 'Ignore Order,' this framework wll be of value.
  
# Features 
* Simple difference result structure divided by type ( new, missing and modified fields )
* Ability to ignore specific path  
* Ability to ignore order difference in particular path
* The project use the same JSON Comparison Engine as used in https://github.com/skyscreamer/JSONassert 

--- 
# Examples

```
String expectedJson = "{}";
    String actualJson = "{}";
    
    JsonComparisonResult compareResult = JsonCompare.builder()
        .addFilter(new IgnoredNewValuesFilter(Paths.createRegexPaths("root\\.ignore\\.new\\.field")))
        .addFilter(new IgnoredPathFilter(Paths.createRegexPaths("root\\.ignore\\.path\\..*")))  // .* match the prefix root.ignore.path.value 
        .ignoreOrder(Paths.createRegexPaths("root\\.array"))
        .build()
        .compare(expectedJson, actualJson);

    boolean isMatch = compareResult.isMatch();   
```


# Filters
Json Comparison supports 4 kind of filters
* Ignore new field/value filter IgnoredNewValueFilter.class - ignore diff on a new field/value (exists in actual JSON only)
* Ignore missing/value field filter IgnoredMissingValuesFilter.class - ignore diff on a missing field or value (exists in expected JSON only)
* Ignore modified values filter IgnoredModifiedValuesFilter.class - ignore diff on a modified value (exists in actual or expected JSON)
* Ignore path filter IgnoredPathFilter.class -  ignore diff field or value in both sides 

Note: also supports custom Filter


# Order
Json Comparison, by default, uses a STRICT mode - all arrays in JSON should preserve order. 
In order to ignore order use the ignoreOrder option.


# Paths
<p>
Json Comparison supports Json Path syntax, since Paths are regular expression paths, and backslash is required for an escape character See https://www.vogella.com/tutorials/JavaRegularExpressions/article.html#backslashes-in-java
</p> 

 
```Json
 Path for secondElement is rootElement\\.secondElement

  {
     "rootElement": {
       "secondElement" : "value"
    }
 } 
```

```JSON
  Path for first item value in array "rootElement\\.array\\[1\\]\\.item.value"

    {
        "rootElement": {
           "array": [
              {
                 "item": "value"
              },
              {
                 "item": "value2"
              }
           ]
        }
     }
```

```JSON
Path for all elements start with A     rootElement\\.startWithA.*

 {
     "rootElement": {
        "startWithA1": {
           "item": "value"
        },
        "startWithA2": {
           "item": "value"
        }
     }
  }
```

##### Note: Json Comparison always expects a full Path always. If you provide a partial path, then add a suffix .* to match the rest of the path.  

# Results - CompareJsonResult
Json Comparison returns 3 collections 
* modifiedFields - fields that have different values
* missingFields - fields that are missing in expected JSON
* newFields - fields that are extra in expected JSON, but do not exists in actual JSON

# QuickStart
To use, download the JAR, or add the following to your project's pom.xml:
```
    <dependency>
        <groupId>com.json.comparison</groupId>
        <artifactId>json-comparison</artifactId>
        <version>1.2</version>
    </dependency>
```

Write comparison of two strings:
```

 JsonComparisonResult compareResult = JsonCompare.builder()
        .build()
        .compare(expectedJson, actualJson);
```

#New Contributions
 New contributions from the community are welcome.<br/>
 Ideas for features that could be implemented by contributors include:
 * Improve Path syntax
 * Ability to compare particular path only
 * Creating GUI for the result 

# License
Copyright 2019 eBay Inc. <BR>
Author/Developer: Ahmad Mahagna

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

https://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

# Notice of 3rd Party Code Use
This project includes code from the open source project(s) listed below.

JSONAssert <BR>
========== <BR>
URL: https://github.com/skyscreamer/JSONassert <BR>
LICENSE: Apache License, found at: https://github.com/skyscreamer/JSONassert/blob/master/LICENSE.txt
