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

import com.eclipsesource.schema.drafts.Version4
import com.eclipsesource.schema.test.JsonSpec
import play.api.test.PlaySpecification

class AjvSpecs extends PlaySpecification with JsonSpec {

  import Version4._
  implicit val validator: com.eclipsesource.schema.SchemaValidator = SchemaValidator(Some(Version4))
  def validateAjv(testName: String) = validate(testName, "ajv_tests")

  validateAjv("1_ids_in_refs")
  validateAjv("2_root_ref_in_ref")
  validateAjv("17_escaping_pattern_property")
  validateAjv("19_required_many_properties")
  validateAjv("20_failing_to_parse_schema")
  validateAjv("27_recursive_reference")
  validateAjv("27_1_recursive_raml_schema")
  validateAjv("28_escaping_pattern_error")
  validateAjv("33_json_schema_latest")
  validateAjv("63_id_property_not_in_schema")
  validateAjv("70_1_recursive_hash_ref_in_remote_ref")
  validateAjv("70_swagger_schema")
  validateAjv("87_$_property")
  validateAjv("94_dependencies_fail")
  validateAjv("170_ref_and_id_in_sibling")
  validateAjv("226_json_with_control_chars")

}
