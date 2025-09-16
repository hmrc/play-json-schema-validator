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
import com.eclipsesource.schema.internal.{Keywords, Results, SchemaUtil, ValidatorMessages}
import com.eclipsesource.schema.{SchemaArray, SchemaResolutionContext}
import com.osinka.i18n.Lang
import play.api.libs.json.{JsArray, JsValue}
import scalaz.{Failure, Success}
import com.eclipsesource.schema.SchemaTypeExtensionOps

object ArrayValidator extends SchemaTypeValidator[SchemaArray] {

  override def validate(schema: SchemaArray, json: => JsValue, context: SchemaResolutionContext)(implicit lang: Lang): VA[JsValue] = {
    json match {
      case JsArray(values) =>
        val elements: Seq[VA[JsValue]] = values.toSeq.zipWithIndex.map { case (jsValue, idx) =>
          schema.item.validate(
            jsValue,
            context.updateScope(
              _.copy(instancePath = context.instancePath \ idx.toString)
            )
          )
        }
        if (elements.exists(_.isFailure)) {
          Failure(elements.collect { case Failure(err) => err }.reduceLeft(_ ++ _))
        } else {
          val updatedArr = JsArray(elements.collect { case Success(js) => js })
          schema.constraints.validate(schema, updatedArr, context)
        }
      case _ =>
        Results.failureWithPath(
          Keywords.Any.Type,
          ValidatorMessages("err.expected.type", SchemaUtil.typeOfAsString(json)),
          context,
          json
        )
    }
  }

}
