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

package com.eclipsesource.schema.test

import play.api.http.{DefaultFileMimeTypes, FileMimeTypesConfiguration}
import play.api.mvc.{DefaultActionBuilder, Handler}

object Assets {

  import play.api.mvc.Results._
  implicit val mimeTypes: play.api.http.DefaultFileMimeTypes = new DefaultFileMimeTypes(FileMimeTypesConfiguration(Map("json" -> "application/json")))

  def routes(Action: DefaultActionBuilder)(clazz: Class[_], prefix: String = ""): PartialFunction[(String, String), Handler] = { case (_, path) =>
    try {
      implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
      val resourceName = prefix + path.substring(1)
      Option(clazz.getClassLoader.getResource(resourceName))
        .map(_ => Action(Ok.sendResource(resourceName, clazz.getClassLoader)))
        .getOrElse(Action(BadRequest(s"$resourceName not found.")))
    } catch {
      case ex: Throwable =>
        Action(BadRequest(ex.getMessage))
    }
  }
}
