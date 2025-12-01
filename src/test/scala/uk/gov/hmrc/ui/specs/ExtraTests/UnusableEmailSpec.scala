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

import uk.gov.hmrc.ui.pages.{Auth, EmailVerification, Registration}
import uk.gov.hmrc.ui.specs.BaseSpec

class UnusableEmailSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification

  Feature("Unusable email status journeys") {

    Scenario("Intermediary changes email address when they are presented with the unusable email intercept page") {

      Given("the intermediary accesses their dashboard")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "unusableEmailStatus", "dashboard")

      When("the intermediary is shown the unusable email intercept page")
      registration.checkDashboardJourneyUrl("intercept-unusable-email")

      And("the intermediary clicks Confirm email address")
      registration.continue()

      Then("the intermediary is redirected to the contact details page of the registration service")
      registration.checkJourneyUrl("contact-details?waypoints=change-your-registration")

      Then("the intermediary updates their email address")
      registration.updateField("emailAddress", "unusable-test@email.com")
      registration.continue()
      email.completeEmailVerification("amend")

      And("the intermediary can submit their amended registration with updated email address")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended")
      registration.checkAmendedAnswers("emailChanged")
    }

    Scenario("Intermediary keeps same email address when they are presented with the unusable email intercept page") {

      Given("the intermediary accesses their dashboard")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "unusableEmailStatus", "dashboard")

      When("the intermediary is shown the unusable email intercept page")
      registration.checkDashboardJourneyUrl("intercept-unusable-email")

      And("the intermediary clicks Confirm email address")
      registration.continue()

      Then("the intermediary is redirected to the contact details page of the registration service")
      registration.checkJourneyUrl("contact-details?waypoints=change-your-registration")

      Then("the intermediary does not update their email address")
      registration.continue()
      email.completeEmailVerification("amend")

      And("the intermediary can submit their amended registration with updated email address")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended")
      registration.checkAmendedAnswers("noAmendedAnswers")
    }
  }
}
