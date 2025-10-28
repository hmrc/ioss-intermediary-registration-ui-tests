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

import uk.gov.hmrc.ui.pages.{Auth, Registration}

class AmendCoreValidationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Core Validation within Amend Registration journeys") {

    Scenario(
      "Intermediary can add already active and quarantined fixed establishments by EU Vat Number when amending their registration"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "standard", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=change-your-registration"
      )

      And("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      Then("the intermediary can add tax details for one EU country that is already active in another member state")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1?waypoints=change-your-registration")
      registration.selectCountry("Portugal")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1?waypoints=change-your-registration")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=change-your-registration")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1?waypoints=change-your-registration")
      registration.enterAnswer("PT111222333")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-your-registration")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")

      And("the intermediary can add tax details for another EU country that is quarantined in another member state")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-fixed-establishment-country/2?waypoints=change-your-registration")
      registration.selectCountry("Lithuania")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2?waypoints=change-your-registration")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/2?waypoints=change-your-registration")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/2?waypoints=change-your-registration")
      registration.enterAnswer("LT999888777")
      registration.checkJourneyUrl("check-tax-details/2?waypoints=change-your-registration")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")

      And("the intermediary can submit their amended registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended")
      registration.checkAmendedAnswers("coreValidationFixedEstablishments")
    }

    Scenario(
      "Intermediary can add already active and quarantined fixed establishments by Tax ID when amending their registration"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "standard", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=change-your-registration"
      )

      And("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      Then("the intermediary can add tax details for one EU country that is already active in another member state")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1?waypoints=change-your-registration")
      registration.selectCountry("Portugal")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1?waypoints=change-your-registration")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=change-your-registration")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/1?waypoints=change-your-registration")
      registration.enterAnswer("123LIS123")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-your-registration")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")

      And("the intermediary can add tax details for another EU country that is quarantined in another member state")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-fixed-establishment-country/2?waypoints=change-your-registration")
      registration.selectCountry("Lithuania")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2?waypoints=change-your-registration")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/2?waypoints=change-your-registration")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/2?waypoints=change-your-registration")
      registration.enterAnswer("ABC123123")
      registration.checkJourneyUrl("check-tax-details/2?waypoints=change-your-registration")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")

      And("the intermediary can submit their amended registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended")
      registration.checkAmendedAnswers("coreValidationFixedEstablishments")
    }

    Scenario(
      "Intermediary can add already active and quarantined previous registrations when amending their registration"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "standard", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for Other IOSS intermediary registrations")
      registration.selectChangeOrRemoveLink(
        "has-previously-registered-as-intermediary\\?waypoints\\=change-your-registration"
      )

      And("the intermediary selects yes on the has-previously-registered-as-intermediary")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary adds a previous registration that is already active in another member state")
      registration.checkJourneyUrl("previous-eu-country/1?waypoints=change-your-registration")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1?waypoints=change-your-registration")
      registration.enterAnswer("IN7057777123")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")

      And("the intermediary adds a previous registration that is quarantined in another member state")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/2?waypoints=change-your-registration")
      registration.selectCountry("Latvia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2?waypoints=change-your-registration")
      registration.enterAnswer("IN4287777123")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary can submit their amended registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended")
      registration.checkAmendedAnswers("coreValidationPreviousRegistrations")
    }

    Scenario(
      "Intermediary with existing EU intermediary registration linked to UK VRN is able to access the amend intermediary registration journey"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("333333333", "Organisation", "standard", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary submits their registration without amending any details")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      Then("the confirmation of no answers changed is displayed")
      registration.checkAmendedAnswers("noAmendedAnswers")
    }

    Scenario(
      "Intermediary with existing EU intermediary quarantine linked to UK VRN is able to access the amend intermediary registration journey"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("333333334", "Organisation", "standard", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary submits their registration without amending any details")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      Then("the confirmation of no answers changed is displayed")
      registration.checkAmendedAnswers("noAmendedAnswers")
    }
  }
}
