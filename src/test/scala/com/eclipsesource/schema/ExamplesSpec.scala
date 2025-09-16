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

class ExamplesSpec extends Specification {

  val validator = SchemaValidator(Some(Version4))
  val swaggerSchemaUrl = "/test-schemas/swagger-2.0"

  private def validateExample(schema: String, url: String) = {
    val schemaUrl = getClass.getResource(url)
    val instanceUrl = getClass.getResource(url)
    val instance = JsonSource.fromUrl(instanceUrl)
    val result   = validator.validate(schemaUrl)(instance.get)
    result.isSuccess must beTrue
    result.get must beEqualTo(instance.get)
  }

  "Validator" should {
    "validate petstore-minimal" in {
      validateExample(swaggerSchemaUrl, "/test-schemas/petstore-minimal.json")
    }

    "validate petstore-simple" in {
      validateExample(swaggerSchemaUrl, "/test-schemas/petstore-simple.json")
    }

    "validate petstore-expanded" in {
      validateExample(swaggerSchemaUrl, "/test-schemas/petstore-expanded.json")
    }

    "validate petstore-with-external-docs" in {
      validateExample(swaggerSchemaUrl, "/test-schemas/petstore-with-external-docs.json")
    }

    "validate petstore" in {
      validateExample(swaggerSchemaUrl, "/test-schemas/petstore.json")
    }

    "validate core schema agsinst itself" in {
      validateExample("/test-schemas/schema", "/test-schemas/schema")
    }
  }

}
