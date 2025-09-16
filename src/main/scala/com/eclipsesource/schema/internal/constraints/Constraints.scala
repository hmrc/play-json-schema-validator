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

package com.eclipsesource.schema.internal.constraints

import com.eclipsesource.schema.internal.validation.VA
import com.osinka.i18n.Lang
import play.api.libs.json._
import scalaz.Success

object Constraints {

  import com.eclipsesource.schema._

  trait HasAnyConstraint extends Constraint {
    def any: AnyConstraints
    def id: Option[String] = any.id
    def schemaType: Option[String] = any.schemaType
  }

  trait Constraint {
    def id: Option[String]
    def schemaType: Option[String]
    def validate(schemaType: SchemaType, json: JsValue, context: SchemaResolutionContext)
                (implicit lang: Lang): VA[JsValue]
    private[schema] def subSchemas: Set[SchemaType]
    private[schema] def resolvePath(path: String): Option[SchemaType]
  }

  case class NoConstraints() extends Constraint {
    override def subSchemas: Set[SchemaType] = Set.empty
    override def resolvePath(path: String): Option[SchemaType] = None
    override def validate(schema: SchemaType, json: JsValue, context: SchemaResolutionContext)
                         (implicit lang: Lang): VA[JsValue] = Success(json)
    override def id: Option[String] = None
    override def schemaType: Option[String] = None
  }

  trait AnyConstraints extends Constraint

  trait ArrayConstraints extends Constraint with HasAnyConstraint

  trait ObjectConstraints extends Constraint with HasAnyConstraint

  trait NumberConstraints extends Constraint

  trait StringConstraints extends Constraint

  case class Minimum(min: BigDecimal, isExclusive: Option[Boolean])

  case class Maximum(max: BigDecimal, isExclusive: Option[Boolean])

  case class MultipleOf(factor: BigDecimal)
}