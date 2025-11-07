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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.*

class GGLoginKickoutSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Government Gateway Login Kickout journeys") {

    Scenario("An intermediary with no VAT enrolment cannot access the service") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service with no VAT enrolment")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("None", "Organisation", "noVat", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary is on the credential-unsupported page")
      registration.checkJourneyUrl("credential-unsupported")
    }

    Scenario(
      "An intermediary who is already registered for this service cannot access the registration journey again"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service with an IOSS intermediary enrolment")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "standard", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary is on the already-registered page")
      registration.checkJourneyUrl("already-registered")
    }

    Scenario("An intermediary with an expired VAT registration cannot access the service") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service with an expired VRN")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000004", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary is on the expired-vrn-date page")
      registration.checkJourneyUrl("expired-vrn-date")
    }

  }
}
