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

import uk.gov.hmrc.ui.pages.{Auth, Registration}

class GeneralKickoutSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("General kickout journeys") {

    Scenario("Intermediary does not have a fixed establishment") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      And("the intermediary selects no on the have-uk-trading-name page")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")

      // currently redirects to Check Your Answers page as full journey isn't developed yet

      // adding manual browsing to EU details section as previous registration section is not developed yet
      Then("the intermediary selects yes on the tax-in-eu page")
      registration.goToPage("tax-in-eu")
      registration.checkJourneyUrl("tax-in-eu")
      registration.answerRadioButton("yes")

      And("the intermediary selects Austria on the first eu-tax page")
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Austria")

      And("the intermediary selects no on the first eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment/1")
      registration.answerRadioButton("no")

      Then("the intermediary is on the cannot-register-no-fixed-establishment page")
      registration.checkJourneyUrl("cannot-register-no-fixed-establishment/1")

    }

  }

}
