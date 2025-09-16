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
import com.eclipsesource.schema.internal.{Keywords, Results, ValidatorMessages}
import com.eclipsesource.schema.{CompoundSchemaType, _}
import com.osinka.i18n.Lang
import play.api.libs.json.JsValue

object CompoundValidator extends SchemaTypeValidator[CompoundSchemaType] {
  override def validate(schema: CompoundSchemaType, json: => JsValue, context: SchemaResolutionContext)(implicit lang: Lang): VA[JsValue] = {
    val result: Option[VA[JsValue]] = schema.alternatives
      .map(_.validate(json, context))
      .find(_.isSuccess)

    result.getOrElse(
      Results.failureWithPath(
        Keywords.Any.Type,
        ValidatorMessages("comp.no.schema"),
        context,
        json
      )
    )
  }
}
