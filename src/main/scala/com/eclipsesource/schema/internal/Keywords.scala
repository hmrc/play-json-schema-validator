/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eclipsesource.schema.internal

object Keywords {

  val Schema = "$schema"
  val Description = "description"

  val Default = "default"
  val Ref = "$ref"

  object Object {
    val Properties = "properties"
    val PatternProperties = "patternProperties"
    val AdditionalProperties = "additionalProperties"
    val Required = "required"
    val Dependencies = "dependencies"
    val MinProperties = "minProperties"
    val MaxProperties = "maxProperties"
    val PropertyNames = "propertyNames"
  }

  object Any {
    val AllOf = "allOf"
    val AnyOf = "anyOf"
    val OneOf = "oneOf"
    val Not = "not"
    val Definitions = "definitions"
    val Description = "description"
    val Enum = "enum"
    val Type = "type"
    val If = "if"
    val Then = "then"
    val Else = "else"
  }

  object Number {
    val Min = "minimum"
    val Max = "maximum"
    val ExclusiveMin = "exclusiveMinimum"
    val ExclusiveMax = "exclusiveMaximum"
    val MultipleOf = "multipleOf"
  }

  object String {
    val MinLength = "minLength"
    val MaxLength = "maxLength"
    val Pattern = "pattern"
    val Format = "format"
  }

  object Array {
    val AdditionalItems = "additionalItems"
    val MinItems = "minItems"
    val MaxItems = "maxItems"
    val UniqueItems = "uniqueItems"
    val Items = "items"
    val Contains = "contains"
  }

}