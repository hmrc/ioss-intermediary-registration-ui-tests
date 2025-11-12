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

import uk.gov.hmrc.ui.pages.{Auth, Registration}
import uk.gov.hmrc.ui.specs.BaseSpec

class RegistrationFailureSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Registration failure journeys") {

    Scenario("Failure from ETMP on submission of registration") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("666000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary completes the all of the registration answers and submits")
      registration.submitMinimalRegistration()

      Then("the intermediary is on the error-submitting-registration page")
      registration.checkJourneyUrl("error-submitting-registration")
    }

    Scenario("Enrolment failure on submission of registration and returning to saved registration") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("666000003", "Organisation", "vatOnly", "registrationFailure")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary completes the all of the registration answers and submits")
      registration.submitMinimalRegistration()

      Then("the intermediary is on the error-submitting-registration page")
      registration.checkJourneyUrl("error-submitting-registration")

      Given("the intermediary logs into IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("666000003", "Organisation", "vatOnly", "registrationFailureSave")

      Then("the intermediary is on the continue-registration page")
      registration.checkJourneyUrl("continue-registration")

      And("the intermediary selects yes to continue with their saved registration")
      registration.clickLink("continueProgress")
      registration.continue()
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()
      registration.checkJourneyUrl("error-submitting-registration")
    }
  }
}
