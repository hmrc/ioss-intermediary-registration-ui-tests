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

class AmendRegistrationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Amend registration journeys") {

    Scenario("Intermediary can access the amend registration journey") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossInt", "amend")
      registration.checkJourneyUrl("change-your-registration")

    }

    Scenario("Intermediary can cancel their amended registration") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossInt", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks cancel on the change-your-registration page")
      registration.clickLink("cancel")

      And("the intermediary selects yes on the cancel-amend-registration page")
      registration.answerRadioButton("yes")

      Then("the intermediary is redirected to the Intermediary dashboard service")
      registration.checkDashboardJourneyUrl("your-account")

    }
  }
}
