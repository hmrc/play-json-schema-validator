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

package com.eclipsesource.schema.internal.draft4.constraints

import com.eclipsesource.schema.internal.Keywords
import com.eclipsesource.schema.{SchemaArray, SchemaResolutionContext, SchemaTuple, SchemaType, SchemaValue}
import com.eclipsesource.schema.internal.constraints.Constraints.{AnyConstraints, ArrayConstraints, Constraint}
import com.eclipsesource.schema.internal.validation.VA
import com.eclipsesource.schema.internal.validators.TupleValidators
import com.osinka.i18n.Lang
import play.api.libs.json.{JsBoolean, JsNumber, JsValue}
import scalaz.Success

case class ArrayConstraints4(
    maxItems: Option[Int] = None,
    minItems: Option[Int] = None,
    additionalItems: Option[SchemaType] = None,
    unique: Option[Boolean] = None,
    any: AnyConstraints = AnyConstraints4()
) extends ArrayConstraints
    with Constraint {

  import com.eclipsesource.schema.internal.validators.ArrayConstraintValidators._

  override def subSchemas: Set[SchemaType] =
    additionalItems.map(Set(_)).getOrElse(Set.empty) ++ any.subSchemas

  override def validate(schema: SchemaType, json: JsValue, resolutionContext: SchemaResolutionContext)(implicit lang: Lang): VA[JsValue] = {

    val reader = for {
      minItemsRule <- validateMinItems(minItems)
      maxItemsRule <- validateMaxItems(maxItems)
      uniqueRule <- validateUniqueness(unique)
    } yield {
      minItemsRule |+| maxItemsRule |+| uniqueRule
    }

    schema match {
      case t: SchemaTuple =>
        TupleValidators
          .validateTuple(additionalItems, t)
          .flatMap(x => reader.map(f => f |+| x))
          .run(resolutionContext)
          .repath(_.compose(resolutionContext.instancePath))
          .validate(json)
      case _: SchemaArray =>
        reader
          .run(resolutionContext)
          .repath(_.compose(resolutionContext.instancePath))
          .validate(json)
      case _ => Success(json)
    }
  }

  def resolvePath(path: String): Option[SchemaType] = path match {
    case Keywords.Array.MinItems        => minItems.map(min => SchemaValue(JsNumber(min)))
    case Keywords.Array.MaxItems        => maxItems.map(max => SchemaValue(JsNumber(max)))
    case Keywords.Array.AdditionalItems => additionalItems
    case Keywords.Array.UniqueItems     => unique.map(u => SchemaValue(JsBoolean(u)))
    case other                          => any.resolvePath(other)
  }
}
