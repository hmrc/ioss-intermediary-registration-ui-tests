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

import uk.gov.hmrc.ui.pages._

class VatDetailsSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("VAT details journeys") {

    Scenario("VAT details showing wrong business") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary answers that they want to register a different business")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("No, I want to register a different business")
      registration.checkJourneyUrl("register-different-business")
    }

    Scenario("VAT details showing incorrect details") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary answers that some of the VAT details are incorrect")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes, but some of my VAT details are incorrect")
      registration.checkJourneyUrl("update-vat-details")
    }

    Scenario("VAT details not found for VRN") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("900000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary is on the registration-service-error page")
      registration.checkJourneyUrl("registration-service-error")
    }

    Scenario("Internal server error when retrieving VAT details") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("800000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary is on the registration-service-error page")
      registration.checkJourneyUrl("registration-service-error")
    }

    Scenario("Missing VAT details - no registration start date") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary is on the registration-service-error page")
      registration.checkJourneyUrl("registration-service-error")
    }

    Scenario(
      "Individual user can access the IOSS Intermediary Registration Service and their Individual Name is displayed instead of an Organisation Name"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service as an Individual")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000002", "Individual", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary has access to the confirm-vat-details page and the individual name is displayed")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.checkIndividualName()
    }
  }
}
