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

import uk.gov.hmrc.ui.pages._
import uk.gov.hmrc.ui.specs.BaseSpec

class AmendExcludedSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification
  private val excludedAmend = ExcludedAmend

  Feature("Amend registration for excluded intermediary journeys") {

    Scenario("Excluded intermediary with a manual NI address can only amend their address, bank and contact details") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "excludedNiManual", "amend")

      When("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")

      Then("they have the correct change links for an excluded intermediary")
      excludedAmend.checkChangeLinksManualNi()

      And("the intermediary clicks change on the Business address in Northern Ireland")
      registration.selectChangeOrRemoveLink("ni-address\\?waypoints\\=change-your-registration")

      And("the intermediary changes some of their Northern Ireland address details")
      registration.checkJourneyUrl("ni-address?waypoints=change-your-registration")
      registration.checkNiAddressText(false)
      registration.checkNiAddressH1(false)
      registration.updateField("townOrCity", "Belfast")
      registration.updateField("county", "")
      registration.updateField("postCode", "BT1 9AA")
      registration.continue()
      registration.checkJourneyUrl("change-your-registration")

      And("the intermediary clicks change for contact details")
      registration.selectChangeOrRemoveLink("contact-details\\?waypoints\\=change-your-registration")

      And("the intermediary changes some of their contact details")
      registration.checkJourneyUrl("contact-details?waypoints=change-your-registration")
      registration.updateField("fullName", "New Name")
      registration.updateField("emailAddress", "new-email@test.com")
      registration.continue()
      email.completeEmailVerification("amend")
      registration.checkJourneyUrl("change-your-registration")

      And("the intermediary clicks change for bank details")
      registration.selectChangeOrRemoveLink("bank-account-details\\?waypoints\\=change-your-registration")

      And("the intermediary changes some of their bank details")
      registration.checkJourneyUrl("bank-account-details?waypoints=change-your-registration")
      registration.updateField("bic", "")
      registration.continue()
      registration.checkJourneyUrl("change-your-registration")

      Then("the intermediary can submit their amended registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended")
      excludedAmend.checkAmendedAnswersExcludedIntermediary("manualNiAddress")
    }

    Scenario("Excluded intermediary with an NI address in their VAT info can only amend their bank and contact details") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "amend")

      When("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")

      Then("they have the correct change links for an excluded intermediary")
      excludedAmend.checkChangeLinksVatInfoNi()
    }

    Scenario("Excluded intermediary cannot access trading names, previous registrations or other EU registrations in amend") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "excludedNiManual", "amend")

      When("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      
      And("the intermediary manually browses to have-other-trading-name?waypoints=change-your-registration")
      registration.goToPage("have-other-trading-name?waypoints=change-your-registration")
      
      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary manually browses to other-trading-name/1?waypoints=add-other-trading-name%2Cchange-your-registration")
      registration.clickBackButton()
      registration.checkJourneyUrl("change-your-registration")
      registration.goToPage("other-trading-name/1?waypoints=add-other-trading-name%2Cchange-your-registration")

      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")
      
      When("the intermediary manually browses to add-other-trading-name?waypoints=change-your-registration")
      registration.clickBackButton()
      registration.checkJourneyUrl("change-your-registration")
      registration.goToPage("add-other-trading-name?waypoints=change-your-registration")

      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary manually browses to has-previously-registered-as-intermediary?waypoints=change-your-registration")
      registration.clickBackButton()
      registration.checkJourneyUrl("change-your-registration")
      registration.goToPage("has-previously-registered-as-intermediary?waypoints=change-your-registration")

      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary manually browses to previous-eu-country/1?waypoints=change-your-registration")
      registration.clickBackButton()
      registration.checkJourneyUrl("change-your-registration")
      registration.goToPage("previous-eu-country/1?waypoints=change-your-registration")

      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary manually browses to add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.clickBackButton()
      registration.checkJourneyUrl("change-your-registration")
      registration.goToPage("add-previous-intermediary-registration?waypoints=change-your-registration")

      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary manually browses to eu-fixed-establishment?waypoints=change-your-registration")
      registration.clickBackButton()
      registration.checkJourneyUrl("change-your-registration")
      registration.goToPage("eu-fixed-establishment?waypoints=change-your-registration")

      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary manually browses to eu-fixed-establishment-country/1?waypoints=change-your-registration")
      registration.clickBackButton()
      registration.checkJourneyUrl("change-your-registration")
      registration.goToPage("eu-fixed-establishment-country/1?waypoints=change-your-registration")

      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")

      When("the intermediary manually browses to add-tax-details?waypoints=change-your-registration")
      registration.clickBackButton()
      registration.checkJourneyUrl("change-your-registration")
      registration.goToPage("add-tax-details?waypoints=change-your-registration")

      Then("the user is redirected to their dashboard")
      registration.checkDashboardJourneyUrl("your-account")
    }
//    add in scenario for changing to non-NI postcode in excluded amend
  }
}
