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

package com.eclipsesource.schema.internal

import com.eclipsesource.schema.SchemaType
import com.osinka.i18n.Lang
import play.api.libs.json.{JsValue, Json, JsonValidationError, Writes}

package object refs {

  implicit class ResolveResultExtensionOps(resolvedResult: ResolvedResult) {
    def toJson(implicit writes: Writes[SchemaType]): JsValue =
      Json.toJson(resolvedResult.resolved)
  }

  implicit class SchemaRefResolverExtensionOps(resolver: SchemaRefResolver) {
    def resolveFromRoot(ref: String, scope: SchemaResolutionScope)
                       (implicit lang: Lang = Lang.Default): Either[JsonValidationError, ResolvedResult] = {
      resolver.resolve(scope.documentRoot, Ref(ref), scope).toEither
    }
  }
}
