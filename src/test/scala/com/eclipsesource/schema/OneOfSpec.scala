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

class OneOfSpec extends Specification with JsonSpec {

  import Version4._

  "oneOf draft4" in {
    implicit val validator: SchemaValidator = SchemaValidator(Some(Version4))
    validate("oneOf", "draft4")
    validate("oneOf", "ajv_tests")
  }

  "oneOf draft7" in {
    import Version7._
    implicit val validator: SchemaValidator = SchemaValidator(Some(Version7))
    validate("oneOf", "draft7")
  }

  "oneOf must be array of objects (invalid)" in {
    val schema = JsonSource.schemaFromString(
      """{
        | "oneOf": [
        |  "#/definitions/foo"
        | ]
        |}""".stripMargin)
    schema.isError must beTrue
  }

  "oneOf must be array of objects (valid)" in {
    val schema = JsonSource.schemaFromString(
      """{
        | "oneOf": [{
        |  "$ref": "#/definitions/foo"
        | }]
        |}""".stripMargin)
    schema.isSuccess must beTrue
  }
}
