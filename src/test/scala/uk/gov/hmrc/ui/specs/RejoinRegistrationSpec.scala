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

import uk.gov.hmrc.ui.pages.{Auth, EmailVerification, Registration}

class RejoinRegistrationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification

  Feature("Rejoin registration journeys") {

    Scenario("Intermediary with an exclusion effective date in the past but not quarantined can rejoin the service") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      Then("the intermediary can submit their registration and rejoin the service without changing any details")
      registration.submit()
      registration.checkJourneyUrl("successful-rejoin")
    }

    Scenario("Intermediary with an expired quarantine can rejoin the service") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "quarantineExpired", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      Then("the intermediary can submit their registration and rejoin the service without changing any details")
      registration.submit()
      registration.checkJourneyUrl("successful-rejoin")
    }

    Scenario("Intermediary with an exclusion effective date in the future cannot access the rejoin journey") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedFuture", "rejoin")
      registration.checkJourneyUrl("cannot-rejoin")
    }

    Scenario("Intermediary with an active quarantine cannot access the rejoin journey") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "quarantined", "rejoin")
      registration.checkJourneyUrl("cannot-rejoin")
    }

    Scenario("Intermediary who reversed their exclusion cannot access the rejoin journey") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "reversal", "rejoin")
      registration.checkJourneyUrl("cannot-rejoin")
    }

    Scenario("Intermediary can add new data to their registration when rejoining the service - no to yes") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Have a different UK trading name")
      registration.selectChangeOrRemoveLink(
        "have-other-trading-name\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the have-other-trading-name page")
      registration.checkJourneyUrl("have-other-trading-name?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      And("the intermediary adds two trading names")
      registration.checkJourneyUrl("other-trading-name/1?waypoints=add-other-trading-name%2Crejoin-check-your-details")
      registration.enterAnswer("first rejoin trading name")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("other-trading-name/2?waypoints=add-other-trading-name%2Crejoin-check-your-details")
      registration.enterAnswer("rejoin trading 2!")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Other IOSS intermediary registrations")
      registration.selectChangeOrRemoveLink(
        "has-previously-registered-as-intermediary\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the has-previously-registered-as-intermediary")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      And("the intermediary adds two previous registrations")
      registration.checkJourneyUrl("previous-eu-country/1?waypoints=rejoin-check-your-details")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1?waypoints=rejoin-check-your-details")
      registration.enterAnswer("IN7051234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/2?waypoints=rejoin-check-your-details")
      registration.selectCountry("Netherlands")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2?waypoints=rejoin-check-your-details")
      registration.enterAnswer("IN5281234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      Then("the intermediary can add tax details for one EU country")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1?waypoints=rejoin-check-your-details")
      registration.selectCountry("Denmark")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1?waypoints=rejoin-check-your-details")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=rejoin-check-your-details")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1?waypoints=rejoin-check-your-details")
      registration.enterAnswer("DK12345678")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=rejoin-check-your-details")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=rejoin-check-your-details")

      And("the intermediary can add tax details for another EU country")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-fixed-establishment-country/2?waypoints=rejoin-check-your-details")
      registration.selectCountry("Slovakia")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2?waypoints=rejoin-check-your-details")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/2?waypoints=rejoin-check-your-details")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/2?waypoints=rejoin-check-your-details")
      registration.enterAnswer("12345A")
      registration.checkJourneyUrl("check-tax-details/2?waypoints=rejoin-check-your-details")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("rejoin-check-your-details")

      Then("the intermediary can submit their registration and rejoin the service with their amended details")
      registration.submit()
      registration.checkJourneyUrl("successful-rejoin")
    }

    Scenario("Intermediary can amend their existing data when rejoining the service") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedFullData", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for add-other-trading-name")
      registration.selectChangeOrRemoveLink(
        "add-other-trading-name\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary clicks remove for the first trading name")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=rejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "remove-other-trading-name\\/1\\?waypoints\\=rejoin-check-your-details"
      )

      Then("the intermediary selects yes on the remove-other-trading-name page")
      registration.checkJourneyUrl("remove-other-trading-name/1?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for the first trading name")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=rejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "other-trading-name\\/1\\?waypoints\\=change-add-other-trading-name\\%2Crejoin-check-your-details"
      )

      And("the intermediary amends the first trading name")
      registration.checkJourneyUrl(
        "other-trading-name/1?waypoints=change-add-other-trading-name%2Crejoin-check-your-details"
      )
      registration.enterAnswer("a rejoin trading name")

      And("the intermediary selects no on the add-other-trading-name page")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("no")

      When("the intermediary clicks change for add-tax-details")
      registration.checkJourneyUrl("rejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "add-tax-details\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary clicks remove for the second country")
      registration.checkJourneyUrl("add-tax-details?waypoints=rejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "remove-tax-details\\/2\\?waypoints\\=rejoin-check-your-details"
      )

      Then("the intermediary selects yes on the remove-tax-details page")
      registration.checkJourneyUrl("remove-tax-details/2?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for the first country")
      registration.checkJourneyUrl("add-tax-details?waypoints=rejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "check-tax-details\\/1\\?waypoints\\=change-add-tax-details\\%2Crejoin-check-your-details"
      )

      Then("the intermediary clicks change for the first country")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-add-tax-details%2Crejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment-country\\/1\\?waypoints\\=check-tax-details-1\\%2Cchange-add-tax-details\\%2Crejoin-check-your-details"
      )

      And("the intermediary changes the country to Malta")
      registration.checkJourneyUrl(
        "eu-fixed-establishment-country/1?waypoints=check-tax-details-1%2Cchange-add-tax-details%2Crejoin-check-your-details"
      )
      registration.clearCountry()
      registration.selectCountry("Malta")

      And("the intermediary enters the fixed establishment details on the eu-fixed-establishment-address page")
      registration.checkJourneyUrl(
        "eu-fixed-establishment-address/1?waypoints=check-tax-details-1%2Cchange-add-tax-details%2Crejoin-check-your-details"
      )
      registration.enterFETradingName("Malta Business")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Valletta", "", "")

      And("the intermediary selects tax id number on the registration-tax-type page")
      registration.checkJourneyUrl(
        "registration-tax-type/1?waypoints=check-tax-details-1%2Cchange-add-tax-details%2Crejoin-check-your-details"
      )
      registration.selectRegistrationType("tax id number")

      And("the intermediary adds the tax identification number for Malta")
      registration.checkJourneyUrl(
        "eu-tax-identification-number/1?waypoints=check-tax-details-1%2Cchange-add-tax-details%2Crejoin-check-your-details"
      )
      registration.enterAnswer("123 MT 123")

      And("the intermediary selects continue on the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()

      Then("the intermediary selects no on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for contact details")
      registration.selectChangeOrRemoveLink(
        "contact-details\\?waypoints\\=rejoin-check-your-details"
      )

      Then("the intermediary can update their name and email address")
      registration.checkJourneyUrl("contact-details?waypoints=rejoin-check-your-details")
      registration.updateField("fullName", "Amended Test Name")
      registration.updateField("emailAddress", "amend-test@email.com")
      registration.continue()
      email.completeEmailVerification("rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for bank details")
      registration.selectChangeOrRemoveLink(
        "bank-account-details\\?waypoints\\=rejoin-check-your-details"
      )

      Then("the intermediary can update their IBAN and remove their BIC code")
      registration.checkJourneyUrl("bank-account-details")
      registration.updateField("bic", "")
      registration.updateField("iban", "GB91BKEN10000041610008")
      registration.continue()

      Then("the intermediary can submit their registration and rejoin the service with their amended details")
      registration.checkJourneyUrl("rejoin-check-your-details")
      registration.submit()
      registration.checkJourneyUrl("successful-rejoin")
    }

    Scenario(
      "Intermediary can remove their existing trading names and fixed establishments data when rejoining the service - yes to no"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedFullData", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Have a different UK trading name")
      registration.selectChangeOrRemoveLink(
        "have-other-trading-name\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects no on the have-other-trading-name page")
      registration.checkJourneyUrl("have-other-trading-name?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the remove-all-trading-names page")
      registration.checkJourneyUrl("remove-all-trading-names?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.checkJourneyUrl("rejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects no on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the remove-all-tax-details page")
      registration.checkJourneyUrl("remove-all-tax-details?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      Then("the intermediary can submit their registration and rejoin the service with their amended details")
      registration.checkJourneyUrl("rejoin-check-your-details")
      registration.submit()
      registration.checkJourneyUrl("successful-rejoin")
    }

    Scenario(
      "Intermediary cannot access the remove-all-previous-intermediary-registrations page to remove all previous registrations"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedFullData", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary attempts to access the remove-all-previous-intermediary-registrations in amend journey")
      registration.goToPage("remove-all-previous-intermediary-registrations?waypoints=rejoin-check-your-details")

      Then("the intermediary is shown the Sorry, there is a problem with the service page")
      registration.checkProblemPage()
    }

    Scenario(
      "Intermediary can add, amend and remove new previous registrations but cannot amend existing previous registrations"
    ) {
      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedFullData", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks add for add-previous-intermediary-registration")
      registration.selectChangeOrRemoveLink(
        "add-previous-intermediary-registration\\?waypoints\\=rejoin-check-your-details"
      )

      Then("there are no change or remove links for the existing previous registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=rejoin-check-your-details")
      registration.checkChangeRemoveLinks()

      And("the intermediary selects yes to add another registration on add-previous-intermediary-registration page")
      registration.answerRadioButton("yes")

      Then("the intermediary selects which country was it registered in on previous eu country page")
      registration.checkJourneyUrl("previous-eu-country/2?waypoints=rejoin-check-your-details")
      registration.selectCountry("Poland")

      Then(
        "the intermediary clicks and enters registration number for Poland on previous-intermediary-registration-number/2 page"
      )
      registration.checkJourneyUrl("previous-intermediary-registration-number/2?waypoints=rejoin-check-your-details")
      registration.enterAnswer("IN6167777777")

      And("the intermediary answers yes on the add-previous-intermediary-registration page")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      Then("the intermediary selects which country was it registered in on previous eu country page")
      registration.checkJourneyUrl("previous-eu-country/3?waypoints=rejoin-check-your-details")
      registration.selectCountry("Croatia")

      Then(
        "the intermediary clicks and enters registration number for Croatia on previous-intermediary-registration-number/2 page"
      )
      registration.checkJourneyUrl("previous-intermediary-registration-number/3?waypoints=rejoin-check-your-details")
      registration.enterAnswer("IN1917777777")

      When("the intermediary selects remove on the second previous registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=rejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "remove-previous-intermediary-registration\\/2\\?waypoints\\=rejoin-check-your-details"
      )

      Then("the intermediary selects yes on the remove-previous-intermediary-registration page")
      registration.checkJourneyUrl("remove-previous-intermediary-registration/2?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for the last previous registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=rejoin-check-your-details")
      registration.selectChangeOrRemoveLink(
        "previous-intermediary-registration-number\\/2\\?waypoints\\=change-add-previous-intermediary-registration\\%2Crejoin-check-your-details"
      )

      And("the intermediary amends the intermediary number")
      registration.checkJourneyUrl(
        "previous-intermediary-registration-number/2?waypoints=change-add-previous-intermediary-registration%2Crejoin-check-your-details"
      )
      registration.enterAnswer("IN1917777722")

      And("the intermediary selects no on the add-previous-intermediary-registration page")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("no")

      Then("the intermediary can submit their registration and rejoin the service with their amended details")
      registration.checkJourneyUrl("rejoin-check-your-details")
      registration.submit()
      registration.checkJourneyUrl("successful-rejoin")
    }

    Scenario("Intermediary with manual Northern Ireland address can amend address to another NI postcode") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "excludedNiManual", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for the manually entered Northern Ireland address")
      registration.selectChangeOrRemoveLink(
        "ni-address\\?waypoints\\=rejoin-check-your-details"
      )

      Then("the intermediary can update their address")
      registration.checkJourneyUrl("ni-address?waypoints=rejoin-check-your-details")
      registration.enterNiAddress("1A Different Road", "Suburb", "Belfast", "", "BT1 1DD")

      Then("the intermediary can submit their registration and rejoin the service with their amended details")
      registration.checkJourneyUrl("rejoin-check-your-details")
      registration.submit()
      registration.checkJourneyUrl("successful-rejoin")
    }

    Scenario("Intermediary with manual Northern Ireland address cannot amend address to a non-NI postcode") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "excludedNiManual", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for the manually entered Northern Ireland address")
      registration.selectChangeOrRemoveLink(
        "ni-address\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary updates the postcode to a non-NI postcode")
      registration.checkJourneyUrl("ni-address?waypoints=rejoin-check-your-details")
      registration.updateField("postCode", "AA1 1AA")
      registration.continue()

      Then("the intermediary is on the cannot-register-not-ni-based-business page")
      registration.checkJourneyUrl("cannot-register-not-ni-based-business")
    }
  }
}
