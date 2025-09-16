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

package com.eclipsesource.schema.urlhandlers

import java.net.{URL, URLConnection, URLStreamHandler}

object ClasspathUrlHandler {
  def Scheme = "classpath"
  def apply = new ClasspathUrlHandler
}

/**
  * URLStreamHandler that looks for a given URL on the classpath.
  */
class ClasspathUrlHandler extends URLStreamHandler {

  override def openConnection(url: URL): URLConnection =
    getClass.getResource(url.getPath).openConnection()
}
