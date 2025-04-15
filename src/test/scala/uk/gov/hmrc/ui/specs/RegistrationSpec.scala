/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.Registration

class RegistrationSpec extends BaseSpec {

  private val registration = Registration

  Feature("Registration journeys") {

    Scenario("Intermediary completes the filter questions in the IOSS Intermediary Registration Service") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      registration.goToRegistrationJourney()
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary selects no on the ioss-intermediary-registered page")
      registration.answerRadioButton("no")

      And("the intermediary selects yes on the registered-for-vat-in-uk page")
      registration.checkJourneyUrl("registered-for-vat-in-uk")
      registration.answerRadioButton("yes")

      And("the intermediary selects yes on the ni-or-eu-based page")
      registration.checkJourneyUrl("ni-or-eu-based")
      registration.answerRadioButton("yes")

      Then("the intermediary presses continue on the register-to-use-service page")
      registration.checkJourneyUrl("register-to-use-service")
      registration.continue()

      // currently redirects back to start of filter questions - get vat info ticket will direct to auth and continue the journey
    }
  }
}
