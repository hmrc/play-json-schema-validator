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

import com.eclipsesource.schema.drafts.Version7
import com.eclipsesource.schema.test.JsonSpec
import org.specs2.mutable.Specification

class RefRemoteDraft7Spec extends Specification with JsonSpec {

  import Version7._

  implicit val validator: SchemaValidator = SchemaValidator(Some(Version7))
    .addSchema(
      "http://localhost:1234/integer.json",
      JsonSource.schemaFromStream(
        getClass.getResourceAsStream("/remotes/integer.json")
      ).get
    )
    .addSchema(
      "http://localhost:1234/subSchemas.json",
      JsonSource.schemaFromStream(
        getClass.getResourceAsStream("/remotes/subSchemas.json")
      ).get
    )
    .addSchema(
      "http://localhost:1234/folder/folderInteger.json",
      JsonSource.schemaFromStream(
        getClass.getResourceAsStream("/remotes/folder/folderInteger.json")
      ).get
    )
    .addSchema(
      "http://localhost:1234/name.json",
      JsonSource.schemaFromStream(
        getClass.getResourceAsStream("/remotes/name.json")
      ).get
    )

  "refRemote draft7" in {
    validate("refRemote", "draft7")
  }
}
