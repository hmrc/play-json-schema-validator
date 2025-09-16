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

import java.net.URL

import com.eclipsesource.schema.drafts.Version4
import org.specs2.mutable.Specification
import play.api.libs.json.{JsValue, Json}

class SimplePerformanceSpec extends Specification {

  import Version4._

  def timed(name: String)(body: => Unit) = {
    val start = System.currentTimeMillis()
    body
    println(name + ": " + (System.currentTimeMillis() - start) + " ms")
  }

  val validator = SchemaValidator(Some(Version4))
  val schemaUrl: URL = getClass.getResource("/issue-99-1.json")
  val schema: SchemaType = JsonSource.schemaFromUrl(schemaUrl).get

  val instance: JsValue = Json.parse("""{ "mything": "the thing" }""".stripMargin)

  timed("preloaded") {
    for (_ <- 1 to 1000) validator.validate(schema, instance)
  }
  timed("url based") {
    for (_ <- 1 to 1000) validator.validate(schemaUrl)(instance)
  }

}
