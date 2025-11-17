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

import uk.gov.hmrc.ui.pages.{Auth, EmailVerification, PreviousRegistration, Registration}
import uk.gov.hmrc.ui.specs.BaseSpec

class MultiplePreviousRegistrationsSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification
  private val previousRegistration = PreviousRegistration

  Feature("Multiple previous registration amend journeys") {

    Scenario("Intermediary with a current registration and one previous registration") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "onePreviousRegistration", "amend")

      When("the intermediary's current registration is displayed")
      registration.checkJourneyUrl("change-your-registration")
      previousRegistration.checkIntermediaryNumber("IN9008230001")

      Then("the intermediary can select the 'View or change your previous registration' link")
      registration.clickLink("change-previous-registrations")

      And("the intermediary selects yes to view their one previous registration")
      registration.checkJourneyUrl("change-your-previous-registration?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary's previous registration is displayed")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9007230001")

      And("change links are only displayed for Contact Details and Bank Details")
      previousRegistration.checkChangeLinks()

    }
  }
}
