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
import com.eclipsesource.schema.internal.constraints.Constraints.Minimum
import com.eclipsesource.schema.internal.draft7.constraints.NumberConstraints7
import com.eclipsesource.schema.{JsonSource, SchemaNumber, SchemaType, SchemaValue}
import org.specs2.mutable.Specification
import play.api.libs.json.{JsBoolean, JsNumber}

class ResolveArrayConstraintsSpec extends Specification {

  "draft v4" should {

    import Version4._
    val resolver = SchemaRefResolver(Version4)

    "resolve array constraints" in {
      val schema = JsonSource.schemaFromString(
        """{
          |  "items": {
          |    "type": "integer"
          |  },
          |  "minItems": 42,
          |  "maxItems": 99,
          |  "additionalItems": false,
          |  "uniqueItems": false
          |}""".stripMargin).get

      val scope = SchemaResolutionScope(schema)
      resolver.resolveFromRoot("#/minItems", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(42)))
      resolver.resolveFromRoot("#/maxItems", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(99)))
      resolver.resolveFromRoot("#/additionalItems", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsBoolean(false)))
      resolver.resolveFromRoot("#/uniqueItems", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsBoolean(false)))
    }
  }

  "draft v7" should {

    import Version7._
    val resolver = SchemaRefResolver(Version7)

    "resolve array constraints" in {
      val schema = JsonSource.schemaFromString(
        """{
          |  "items": {
          |    "type": "integer"
          |  },
          |  "minItems": 42,
          |  "maxItems": 99,
          |  "additionalItems": false,
          |  "uniqueItems": false,
          |  "contains": {
          |    "minimum": 55
          |  }
          |}""".stripMargin).get

      val scope = SchemaResolutionScope(schema)
      resolver.resolveFromRoot("#/minItems", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(42)))
      resolver.resolveFromRoot("#/maxItems", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(99)))
      resolver.resolveFromRoot("#/additionalItems", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsBoolean(false)))
      resolver.resolveFromRoot("#/uniqueItems", scope).map(_.resolved) must beRight[SchemaType](SchemaValue(JsBoolean(false)))
      resolver.resolveFromRoot("#/contains", scope).map(_.resolved) must beRight[SchemaType](
        SchemaNumber(NumberConstraints7(Some(Minimum(BigDecimal(55), Some(false)))))
      )
    }
  }
}
