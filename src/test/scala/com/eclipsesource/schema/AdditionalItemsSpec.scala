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

package com.eclipsesource.schema

import com.eclipsesource.schema.drafts.{Version4, Version7}
import com.eclipsesource.schema.test.JsonSpec
import org.specs2.mutable.Specification
import play.api.libs.json.{JsArray, JsNumber}

class AdditionalItemsSpec extends Specification with JsonSpec {

  "validate draft4" in {
    import Version4._
    implicit val validator: SchemaValidator = SchemaValidator(Some(Version4))
    validate("additionalItems", "draft4")
  }

  "validate draft7" in {
    import Version7._
    implicit val validator: SchemaValidator = SchemaValidator(Some(Version7))
    validate("additionalItems", "draft7")
  }

  "AdditionalItems" should {
    import Version7._
    val schema = JsonSource.schemaFromString(
      """{
        |  "items": [{}, {}, {}],
        |  "additionalItems": false
        |}""".stripMargin).get

    "no additional items present" in {
      val data = JsArray(Seq(JsNumber(1), JsNumber(2), JsNumber(3)))
      SchemaValidator(Some(Version4)).validate(schema, data).isSuccess must beTrue
    }
  }
}
