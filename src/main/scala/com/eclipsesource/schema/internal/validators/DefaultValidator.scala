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

package com.eclipsesource.schema.internal.validators

import com.eclipsesource.schema.internal.validation.VA
import com.eclipsesource.schema.{SchemaResolutionContext, SchemaType}
import com.osinka.i18n.Lang
import play.api.libs.json.JsValue

class DefaultValidator[A <: SchemaType] extends SchemaTypeValidator[A] {
  override def validate(schema: A, json: => JsValue, context: SchemaResolutionContext)(implicit lang: Lang): VA[JsValue] = {
    schema.constraints.validate(schema, json, context)
  }
}
