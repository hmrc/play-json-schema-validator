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

import play.api.libs.json.JsValue

trait SchemaFormat {
  /**
    * The name of the format.
    * @return the format name
    */
  def name: String

  /**
    * Check whether the given value conforms to this format.
    *s
    * @param json the JSON value to be checked
    * @return whether the JSON value conforms to this format
    */
  def validate(json: JsValue): Boolean
}
