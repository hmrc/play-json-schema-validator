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

package com.eclipsesource.schema.drafts

import com.eclipsesource.schema.internal.draft7.{SchemaReads7, SchemaWrites7}
import com.eclipsesource.schema.internal.validators.DefaultFormats
import com.eclipsesource.schema.{JsonSource, SchemaConfigOptions, SchemaFormat, SchemaType, SchemaVersion}

trait Version7 extends SchemaVersion with SchemaReads7 with SchemaWrites7

object Version7 extends Version7 { self =>
  val SchemaUrl = "http://json-schema.org/draft-07/schema#"
  val schemaLocation: String = SchemaUrl
  lazy val Schema: SchemaType =
    JsonSource.schemaFromUrl(self.getClass.getResource("/json-schema-draft-07.json"))
      .getOrElse(throw new RuntimeException("Could not read schema file json-schema-draft-07.json."))
  val options: SchemaConfigOptions = new SchemaConfigOptions {
    override def supportsExternalReferences: Boolean = false
    override def formats: Map[String, SchemaFormat] = DefaultFormats.formats
  }
  def apply(schemaOptions: SchemaConfigOptions): Version7 = {
    new Version7 {
      val schemaLocation: String = SchemaUrl
      override def options: SchemaConfigOptions = schemaOptions
    }
  }
}
