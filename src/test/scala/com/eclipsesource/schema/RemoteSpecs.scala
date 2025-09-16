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
import com.eclipsesource.schema.test.{Assets, JsonSpec}
import org.specs2.mutable.Specification
import org.specs2.specification.AfterAll
import org.specs2.specification.core.Fragments
import org.specs2.specification.dsl.Online
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.DefaultActionBuilder
import play.api.test.TestServer

class RemoteSpecs extends Specification with JsonSpec with Online with AfterAll {

  import Version4._

  implicit val validator: SchemaValidator = {
    SchemaValidator(Some(Version4)).addSchema(
      "http://localhost:1234/scope_foo.json",
      JsonSource
        .schemaFromString(
          """{
          |  "definitions": {
          |    "bar": { "type": "string" }
          |  }
          |}""".stripMargin
        )
        .get
    )
  }

  def createApp: Application = new GuiceApplicationBuilder()
    .configure("play.server.provider" -> "play.core.server.PekkoHttpServerProvider")
    .appRoutes(app => {
      val Action = app.injector.instanceOf[DefaultActionBuilder]
      Assets.routes(Action)(getClass, "remotes/")
    })
    .build()

  lazy val server = TestServer(port = 1234, createApp)

  def afterAll(): Unit = {
    server.stop()
    Thread.sleep(1000)
  }

  def validateAjv(testName: String): Fragments = validate(testName, "ajv_tests")

  sequential

  "Validation from remote resources is possible" >> {
    {
      server.start()
      Thread.sleep(1000)
    } must not(throwAn[Exception]) continueWith {
      validateMultiple(
        "ajv_tests" -> Seq(
          "5_adding_dependency_after",
          "5_recursive_references",
          "12_restoring_root_after_resolve",
          "13_root_ref_in_ref_in_remote_ref",
          "14_ref_in_remote_ref_with_id",
          "62_resolution_scope_change"
        ),
        "draft4" -> Seq("refRemote")
      )
    }
  }

  validateAjv("1_ids_in_refs")
}
