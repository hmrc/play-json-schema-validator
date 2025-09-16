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

package com.eclipsesource.schema.internal.refs

import com.eclipsesource.schema.drafts.{Version4, Version7}
import com.eclipsesource.schema.{JsonSource, SchemaType, SchemaValue}
import org.specs2.mutable.Specification
import play.api.libs.json.JsNumber

class ResolveNumberConstraintsSpec extends Specification {

  "draft v4" should {

    import Version4._
    val resolver = SchemaRefResolver(Version4)

    "resolve number constraints" in {
      val schema = JsonSource.schemaFromString(
        """{
          |  "type": "integer",
          |  "minimum": 0,
          |  "maximum": 10,
          |  "multipleOf": 2
          |}""".stripMargin).get

      val scope = SchemaResolutionScope(schema)
      resolver.resolveFromRoot("#/minimum", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(0)))
      resolver.resolveFromRoot("#/maximum", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(10)))
      resolver.resolveFromRoot("#/multipleOf", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(2)))
    }
  }

  "draft v7" should {

    import Version7._
    val resolver = SchemaRefResolver(Version7)

    "resolve number constraints" in {
      val schema = JsonSource.schemaFromString(
        """{
          |  "type": "integer",
          |  "minimum": 0,
          |  "maximum": 10,
          |  "multipleOf": 2
          |}""".stripMargin).get

      val scope = SchemaResolutionScope(schema)
      resolver.resolveFromRoot("#/minimum", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(0)))
      resolver.resolveFromRoot("#/maximum", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(10)))
      resolver.resolveFromRoot("#/multipleOf", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(2)))
    }
  }
}
