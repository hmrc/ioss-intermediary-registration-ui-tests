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

import uk.gov.hmrc.ui.pages.{Auth, EmailVerification, Registration}

class AmendRegistrationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification

  Feature("Amend registration journeys") {

    Scenario("Intermediary can amend their registration - changing answers from no to yes") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossInt", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for Have a different UK trading name")
      registration.selectChangeOrRemoveLink(
        "have-uk-trading-name\\?waypoints\\=change-your-registration"
      )

      And("the intermediary selects yes on the have-uk-trading-name page")
      registration.checkJourneyUrl("have-uk-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary adds two trading names")
      registration.checkJourneyUrl("uk-trading-name/1?waypoints=add-uk-trading-name%2Cchange-your-registration")
      registration.enterAnswer("first amend trading name")
      registration.checkJourneyUrl("add-uk-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("uk-trading-name/2?waypoints=add-uk-trading-name%2Cchange-your-registration")
      registration.enterAnswer("amend trading 2!")
      registration.checkJourneyUrl("add-uk-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for Other IOSS intermediary registrations")
      registration.selectChangeOrRemoveLink(
        "has-previously-registered-as-intermediary\\?waypoints\\=change-your-registration"
      )

      And("the intermediary selects yes on the has-previously-registered-as-intermediary")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary adds two previous registrations")
      registration.checkJourneyUrl("previous-eu-country/1?waypoints=change-your-registration")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1?waypoints=change-your-registration")
      registration.enterAnswer("IN7051234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/2?waypoints=change-your-registration")
      registration.selectCountry("Netherlands")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2?waypoints=change-your-registration")
      registration.enterAnswer("IN5281234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=change-your-registration"
      )

      And("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      Then("the intermediary can add tax details for one EU country")
      registration.checkJourneyUrl("eu-tax/1?waypoints=change-your-registration")
      registration.selectCountry("Denmark")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1?waypoints=change-your-registration")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=change-your-registration")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1?waypoints=change-your-registration")
      registration.enterAnswer("DK12345678")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-your-registration")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")

      And("the intermediary can add tax details for another EU country")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/2?waypoints=change-your-registration")
      registration.selectCountry("Slovakia")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2?waypoints=change-your-registration")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/2?waypoints=change-your-registration")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/2?waypoints=change-your-registration")
      registration.enterAnswer("12345A")
      registration.checkJourneyUrl("check-tax-details/2?waypoints=change-your-registration")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")

      And("the intermediary can submit their amended registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")
    }

    Scenario("Intermediary can amend their bank and contact details") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossInt", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for contact details")
      registration.selectChangeOrRemoveLink(
        "contact-details\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can update their name and email address")
      registration.checkJourneyUrl("contact-details")
      registration.updateField("fullName", "Amended Test Name")
      registration.updateField("emailAddress", "amend-test@email.com")
      registration.continue()
      email.completeEmailVerification()
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for bank details")
      registration.selectChangeOrRemoveLink(
        "bank-account-details\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can update their IBAN and remove their BIC code")
      registration.checkJourneyUrl("bank-account-details")
      registration.updateField("bic", "")
      registration.updateField("iban", "GB91BKEN10000041610008")
      registration.continue()

      Then("the intermediary can submit their amended registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

    }

    Scenario(
      "Intermediary can remove all trading names and fixed establishments from their registration - changing answers from yes to no"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossIntAmendAnswers", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for Have a different UK trading name")
      registration.selectChangeOrRemoveLink(
        "have-uk-trading-name\\?waypoints\\=change-your-registration"
      )

      And("the intermediary selects no on the have-uk-trading-name page")
      registration.checkJourneyUrl("have-uk-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the remove-all-trading-names page")
      registration.checkJourneyUrl("remove-all-trading-names?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=change-your-registration"
      )

      And("the intermediary selects no on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the remove-all-tax-details page")
      registration.checkJourneyUrl("remove-all-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary can submit their amended registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")
    }

    Scenario(
      "Intermediary cannot access the remove all previous registrations functionality during an amend journey"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossIntAmendAnswers", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary attempts to access the remove-all-previous-intermediary-registrations in amend journey")
      registration.goToPage("remove-all-previous-intermediary-registrations?waypoints=change-your-registration")

      Then("the intermediary is shown the Sorry, there is a problem with the service page")
      registration.checkProblemPage()
    }

    Scenario("Intermediary can amend and remove trading names and fixed establishments in their registration") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossIntAmendAnswers", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for add-uk-trading-name")
      registration.selectChangeOrRemoveLink(
        "add-uk-trading-name\\?waypoints\\=change-your-registration"
      )

      And("the intermediary clicks remove for the first trading name")
      registration.checkJourneyUrl("add-uk-trading-name?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "remove-uk-trading-name\\/1\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary selects yes on the remove-uk-trading-name page")
      registration.checkJourneyUrl("remove-uk-trading-name/1?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for the first trading name")
      registration.checkJourneyUrl("add-uk-trading-name?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "uk-trading-name\\/1\\?waypoints\\=change-add-uk-trading-name\\%2Cchange-your-registration"
      )

      And("the intermediary amends the first trading name")
      registration.checkJourneyUrl("uk-trading-name/1?waypoints=change-add-uk-trading-name%2Cchange-your-registration")
      registration.enterAnswer("an amended trading name")

      And("the intermediary selects no on the add-uk-trading-name page")
      registration.checkJourneyUrl("add-uk-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      When("the intermediary clicks change for add-tax-details")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink(
        "add-tax-details\\?waypoints\\=change-your-registration"
      )

      And("the intermediary clicks remove for the second country")
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "remove-tax-details\\/2\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary selects yes on the remove-tax-details page")
      registration.checkJourneyUrl("remove-tax-details/2?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for the first country")
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "check-tax-details\\/1\\?waypoints\\=change-add-tax-details\\%2Cchange-your-registration"
      )

      Then("the intermediary clicks change for the first country")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-add-tax-details%2Cchange-your-registration")
      registration.selectChangeOrRemoveLink(
        "eu-tax\\/1\\?waypoints\\=check-tax-details-1\\%2Cchange-add-tax-details\\%2Cchange-your-registration"
      )

      And("the intermediary changes the country to Malta")
      registration.checkJourneyUrl(
        "eu-tax/1?waypoints=check-tax-details-1%2Cchange-add-tax-details%2Cchange-your-registration"
      )
      registration.clearCountry()
      registration.selectCountry("Malta")

      And("the intermediary enters the fixed establishment details on the eu-fixed-establishment-address page")
      registration.checkJourneyUrl(
        "eu-fixed-establishment-address/1?waypoints=check-tax-details-1%2Cchange-add-tax-details%2Cchange-your-registration"
      )
      registration.enterFETradingName("Malta Business")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Valletta", "", "")

      And("the intermediary selects tax id number on the registration-tax-type page")
      registration.checkJourneyUrl(
        "registration-tax-type/1?waypoints=check-tax-details-1%2Cchange-add-tax-details%2Cchange-your-registration"
      )
      registration.selectRegistrationType("tax id number")

      And("the intermediary adds the tax identification number for Slovenia")
      registration.checkJourneyUrl(
        "eu-tax-identification-number/1?waypoints=check-tax-details-1%2Cchange-add-tax-details%2Cchange-your-registration"
      )
      registration.enterAnswer("123 MT 123")

      And("the intermediary selects continue on the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()

      Then("the intermediary selects no on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary can submit their amended registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")
    }

    Scenario(
      "Intermediary can add, amend and remove new previous registrations but cannot amend existing previous registrations"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossIntAmendAnswers", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks add for add-previous-intermediary-registration")
      registration.selectChangeOrRemoveLink(
        "add-previous-intermediary-registration\\?waypoints\\=change-your-registration"
      )

      Then("there are no change or remove links for the existing previous registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.checkChangeRemoveLinks()

      And("the intermediary selects yes to add another registration on add-previous-intermediary-registration page")
      registration.answerRadioButton("yes")

      Then("the intermediary selects which country was it registered in on previous eu country page")
      registration.checkJourneyUrl("previous-eu-country/2?waypoints=change-your-registration")
      registration.selectCountry("Poland")

      Then(
        "the intermediary clicks and enters registration number for Poland on previous-intermediary-registration-number/2 page"
      )
      registration.checkJourneyUrl("previous-intermediary-registration-number/2?waypoints=change-your-registration")
      registration.enterAnswer("IN6167777777")

      And("the intermediary answers yes on the add-previous-intermediary-registration page")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      Then("the intermediary selects which country was it registered in on previous eu country page")
      registration.checkJourneyUrl("previous-eu-country/3?waypoints=change-your-registration")
      registration.selectCountry("Croatia")

      Then(
        "the intermediary clicks and enters registration number for Croatia on previous-intermediary-registration-number/2 page"
      )
      registration.checkJourneyUrl("previous-intermediary-registration-number/3?waypoints=change-your-registration")
      registration.enterAnswer("IN1917777777")

      When("the intermediary selects remove on the second previous registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "remove-previous-intermediary-registration\\/2\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary selects yes on the remove-previous-intermediary-registration page")
      registration.checkJourneyUrl("remove-previous-intermediary-registration/2?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for the last previous registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "previous-intermediary-registration-number\\/2\\?waypoints\\=change-add-previous-intermediary-registration\\%2Cchange-your-registration"
      )

      And("the intermediary amends the intermediary number")
      registration.checkJourneyUrl(
        "previous-intermediary-registration-number/2?waypoints=change-add-previous-intermediary-registration%2Cchange-your-registration"
      )
      registration.enterAnswer("IN1917777722")

      And("the intermediary selects no on the add-previous-intermediary-registration page")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      Then("the intermediary can submit their amended registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")
    }

    Scenario(
      "Intermediary can amend manually entered NI address from original registration"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "amendNIManual", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks change for the manually entered Northern Ireland address")
      registration.selectChangeOrRemoveLink(
        "ni-address\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can update their address")
      registration.checkJourneyUrl("ni-address?waypoints=change-your-registration")
      registration.enterNiAddress("1A Different Road", "Suburb", "Belfast", "", "BT1 1DD")

      And("the intermediary can submit their amended registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")
    }

//    Extra scenarios to add when functionality is implemented
//    Need to check you can't remove previous schemes from etmp and only newly added schemes

    Scenario("Intermediary can cancel their amended registration") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossInt", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks cancel on the change-your-registration page")
      registration.clickLink("cancel")

      And("the intermediary selects yes on the cancel-amend-registration page")
      registration.checkJourneyUrl("cancel-amend-registration")
      registration.answerRadioButton("yes")

      Then("the intermediary is redirected to the Intermediary dashboard service")
      registration.checkDashboardJourneyUrl("your-account")

    }

    Scenario("Cannot access amend journey without being registered for Intermediary Registration service") {

      Given(
        "the user accesses the amend journey within IOSS Intermediary Registration Service without an Intermediary enrolment"
      )
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "amend")

      Then("the user is on the cannot-use-not-registered page")
      registration.checkJourneyUrl("cannot-use-not-registered")
    }
  }
}
