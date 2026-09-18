/*
 * Copyright 2026 HM Revenue & Customs
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

class ReviewRegistrationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Intermediary has not updated their registration for over 2 years") {

    Scenario("Intermediary reviews their registration - does not make changes") {

      Given("the intermediary accesses their dashboard")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "reviewRegistration", "dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary clicks on the Start a return link")
      registration.clickLink("start-a-return")

      And("the intermediary clicks on the Review your registration details link")
      registration.checkDashboardJourneyUrl("review-registration-intercept")
      registration.cssLink("start-amend-journey")

      Then("the intermediary is redirected to the registration service to review their registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.checkAmendRegistrationTitle("review")

      And("the intermediary can confirm their registration without making any amendments")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")
      registration.checkAmendedAnswers("noAmendedAnswers")
    }

    Scenario("Intermediary reviews their registration - makes changes") {

      Given("the intermediary accesses their dashboard")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "reviewRegistration", "dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary clicks on the Start a return link")
      registration.clickLink("start-a-return")

      And("the intermediary clicks on the Review your registration details link")
      registration.checkDashboardJourneyUrl("review-registration-intercept")
      registration.cssLink("start-amend-journey")

      Then("the intermediary is redirected to the registration service to review their registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.checkAmendRegistrationTitle("review")

      When("the intermediary amends their bank account name")
      registration.selectChangeOrRemoveLink("bank-account-details\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("bank-account-details?waypoints=change-your-registration")
      registration.updateField("accountName", "Review Registration Name")
      registration.continue()
      registration.checkJourneyUrl("change-your-registration")

      And("the intermediary can submit the amendments to their registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")
      registration.checkAmendedAnswers("reviewRegistration")
    }
  }
}
