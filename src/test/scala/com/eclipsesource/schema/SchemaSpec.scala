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
import org.specs2.mutable.Specification

class SchemaSpec extends Specification { self =>

  "Schema draft v7" should {
    "validate itself" in {
      import com.eclipsesource.schema.drafts.Version7._
      val schema = JsonSource.fromUrl(self.getClass.getResource("/refs/json-schema-draft-07.json")).get
      val jsonSchema = JsonSource.schemaFromStream(self.getClass.getResourceAsStream("/refs/json-schema-draft-07.json")).get
      implicit val validator: SchemaValidator = SchemaValidator()
      validator.validate(jsonSchema, schema).isSuccess must beTrue
    }
  }

  "Schema draft v4" should {
    "validate itself" in {
      import Version4._
      val schema = JsonSource.fromUrl(self.getClass.getResource("/refs/json-schema-draft-04.json")).get
      val jsonSchema = JsonSource.schemaFromStream(self.getClass.getResourceAsStream("/refs/json-schema-draft-04.json")).get
      implicit val validator: SchemaValidator = SchemaValidator()
      validator.validate(jsonSchema, schema).isSuccess must beTrue
    }
  }
}
