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

package com.osinka.i18n

/** An entity's preferred language.
  *
  * A convenient type class to represent a preferred language of a user or session or whatever.
  *
  * Use it in the companion object:
  *
  * {{{
  * case class User(id: Int, lang: Lang)
  *
  * object User {
  *   implicit object localized extends Localized[User] {
  *     override def locale(user: User) = user.lang
  *   }
  * }
  * }}}
  *
  * @see [[Lang]]
  */
trait Localized[T] {
  def locale(a: T): Lang
}

/** Provides a helper for "localized" objects.
  *
  * For example, for [[Messages]]:
  *
  * {{{
  * Localized(user) { implicit lang =>
  *   Messages("error")
  * }
  * }}}
  *
  * @see [[Lang]]
  */
object Localized {
  def apply[T: Localized, R](a: T)(fn: Lang => R) = {
    val localized = implicitly[Localized[T]]
    fn( localized.locale(a) )
  }

  implicit def optionLocalized[T](implicit localized: Localized[T]): Localized[Option[T]] =
    new Localized[Option[T]] {
      override def locale(o: Option[T]) =
        o map(localized.locale) getOrElse Lang.Default
    }
}