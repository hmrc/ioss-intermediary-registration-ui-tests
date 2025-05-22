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

class AdditionalRegistrationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Additional registration journeys") {

    Scenario("Agent can access the IOSS Intermediary Registration Service") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service as an Agent")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Agent", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary has access to the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.checkVatDetailsPage()

    }
  }
}
