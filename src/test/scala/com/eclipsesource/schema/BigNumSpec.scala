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

import com.eclipsesource.schema.drafts.Version4
import com.eclipsesource.schema.test.JsonSpec
import org.specs2.mutable.Specification
import play.api.libs.json.JsNumber

class BigNumSpec extends Specification with JsonSpec {

  import Version4._
  implicit val validator: SchemaValidator = SchemaValidator(Some(Version4))
  validate("optional/bignum", "draft4")

  "Bignum" should {

    "be an integer" in {
      val schema = JsonSource.schemaFromString(""" {"type": "integer"} """).get
      val instance = JsNumber(BigDecimal("12345678910111213141516171819202122232425262728293031"))
      val result = SchemaValidator(Some(Version4)).validate(schema)(instance)
      result.asOpt must beSome.which(_ == JsNumber(BigDecimal("12345678910111213141516171819202122232425262728293031")))
    }

  }
}
