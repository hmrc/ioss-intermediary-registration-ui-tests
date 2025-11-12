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

package uk.gov.hmrc.ui.specs.ExtraTests

import uk.gov.hmrc.ui.pages.Registration
import uk.gov.hmrc.ui.specs.BaseSpec

class FilterQuestionKickoutSpec extends BaseSpec {

  private val registration = Registration

  Feature("Filter question kickout journeys") {

    Scenario("Intermediary is already registered as an IOSS Intermediary") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      registration.goToRegistrationJourney()
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary selects yes on the ioss-intermediary-registered page")
      registration.answerRadioButton("yes")

      Then("the intermediary is on the cannot-register-already-registered page")
      registration.checkJourneyUrl("cannot-register-already-registered")
    }

    Scenario("Intermediary is not registered for VAT in the UK") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      registration.goToRegistrationJourney()
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary selects no on the ioss-intermediary-registered page")
      registration.answerRadioButton("no")

      And("the intermediary selects no on the registered-for-vat-in-uk page")
      registration.checkJourneyUrl("registered-for-vat-in-uk")
      registration.answerRadioButton("no")

      Then("the intermediary is on the cannot-register-no-vat-in-uk page")
      registration.checkJourneyUrl("cannot-register-no-vat-in-uk")
    }

    Scenario("Intermediary is not based in Northern Ireland or the EU") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      registration.goToRegistrationJourney()
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary selects no on the ioss-intermediary-registered page")
      registration.answerRadioButton("no")

      And("the intermediary selects yes on the registered-for-vat-in-uk page")
      registration.checkJourneyUrl("registered-for-vat-in-uk")
      registration.answerRadioButton("yes")

      And("the intermediary selects no on the ni-or-eu-based page")
      registration.checkJourneyUrl("ni-or-eu-based")
      registration.answerRadioButton("no")

      Then("the intermediary is on the cannot-register-no-ni-or-eu-business page")
      registration.checkJourneyUrl("cannot-register-no-ni-or-eu-business")
    }

  }

}
