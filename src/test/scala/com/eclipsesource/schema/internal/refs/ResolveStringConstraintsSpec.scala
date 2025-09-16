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
import play.api.libs.json.{JsNumber, JsString}

class ResolveStringConstraintsSpec extends Specification {

  "draft v4" should {

    import Version4._
    val resolver = SchemaRefResolver(Version4)

    "resolve string constraints" in {
      val schema =
        JsonSource.schemaFromString(
          """{
            |  "type": "string",
            |  "minLength": 1,
            |  "maxLength": 10,
            |  "pattern": "^(\\([0-9]{3}\\))?[0-9]{3}-[0-9]{4}$"
            |}""".stripMargin).get

      val scope = SchemaResolutionScope(schema)
      resolver.resolveFromRoot("#/minLength", scope)
        .map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(1)))
      resolver.resolveFromRoot("#/maxLength", scope)
        .map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(10)))
      resolver.resolveFromRoot("#/pattern", scope)
        .map(_.resolved) must beRight[SchemaType](SchemaValue(JsString("^(\\([0-9]{3}\\))?[0-9]{3}-[0-9]{4}$")))
    }
  }

  "draft v7" should {

    import Version7._
    val resolver = SchemaRefResolver(Version7)

    "resolve string constraints" in {
      val schema =
        JsonSource.schemaFromString(
          """{
            |  "type": "string",
            |  "minLength": 1,
            |  "maxLength": 10,
            |  "pattern": "^(\\([0-9]{3}\\))?[0-9]{3}-[0-9]{4}$"
            |}""".stripMargin).get

      val scope = SchemaResolutionScope(schema)
      resolver.resolveFromRoot("#/minLength", scope)
        .map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(1)))
      resolver.resolveFromRoot("#/maxLength", scope)
        .map(_.resolved) must beRight[SchemaType](SchemaValue(JsNumber(10)))
      resolver.resolveFromRoot("#/pattern", scope)
        .map(_.resolved) must beRight[SchemaType](SchemaValue(JsString("^(\\([0-9]{3}\\))?[0-9]{3}-[0-9]{4}$")))
    }
  }
}
