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

class ChangeAnswersSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification

  Feature("Changing and removing answers journeys") {

    Scenario("Intermediary changes and removes answers whilst using the IOSS Intermediary Registration Service") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary answers the initial filter questions")
      registration.initialSteps()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      And("the intermediary selects yes on the have-other-trading-name page")
      registration.checkJourneyUrl("have-other-trading-name")
      registration.answerRadioButton("yes")

      And("the intermediary adds the first trading name")
      registration.checkJourneyUrl("other-trading-name/1")
      registration.enterAnswer("1st trading name")

      And("the intermediary selects yes on the add-other-trading-name page")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("yes")

      And("the intermediary adds the second trading name")
      registration.checkJourneyUrl("other-trading-name/2")
      registration.enterAnswer("2nd-trading-name")

      And("the intermediary clicks change on the add-other-trading-name page for first trading name")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.selectChangeOrRemoveLink("other-trading-name\\/1\\?waypoints\\=change-add-other-trading-name")

      And("the intermediary amends the first trading name")
      registration.checkJourneyUrl("other-trading-name/1?waypoints=change-add-other-trading-name")
      registration.enterAnswer("a different first trading name")

      And("the intermediary clicks remove on the add-other-trading-name page for second trading name")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.selectChangeOrRemoveLink("remove-other-trading-name\\/2")

      And("the intermediary selects yes on the remove-other-trading-name page")
      registration.checkJourneyUrl("remove-other-trading-name/2")
      registration.answerRadioButton("yes")

      And("the intermediary selects no on the add-other-trading-name page")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("no")

      Then("the intermediary business answers yes on if ever registered as an IOSS scheme in an Eu country")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")

      And("the intermediary selects on which country was it registered on previous-eu-country page")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Austria")

      And("the  intermediary enters the reg number for Austria")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN0401234567")

      And("the intermediary selects yes on adds another registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("yes")

      And("the intermediary selects on which country was it registered on previous-eu-country page")
      registration.checkJourneyUrl("previous-eu-country/2")
      registration.selectCountry("Belgium")

      Then("the  intermediary enters the reg number for Belgium")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2")
      registration.enterAnswer("IN0561234567")

      And("the intermediary selects yes on adds another registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("yes")

      And("the intermediary selects on which country was it registered on previous-eu-country page")
      registration.checkJourneyUrl("previous-eu-country/3")
      registration.selectCountry("Cyprus")

      Then("the  intermediary enters the reg number for Cyprus")
      registration.checkJourneyUrl("previous-intermediary-registration-number/3")
      registration.enterAnswer("IN1961234567")

      And(
        "the intermediary clicks remove link for Belgium on add-previous-intermediary-registration page"
      )
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.selectChangeOrRemoveLink("remove-previous-intermediary-registration\\/2")

      Then("the intermediary confirms to remove previous intermediary registration for Belgium")
      registration.checkJourneyUrl("remove-previous-intermediary-registration/2")
      registration.answerRadioButton("yes")

      And("the intermediary clicks change link for Cyprus on add-previous-intermediary-registration page")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.selectChangeOrRemoveLink(
        "previous-intermediary-registration-number\\/2\\?waypoints\\=change-add-previous-intermediary-registration"
      )

      Then("the intermediary amends the registration number for Cyprus")
      registration.checkJourneyUrl(
        "previous-intermediary-registration-number/2?waypoints=change-add-previous-intermediary-registration"
      )
      registration.enterAnswer("IN1961231237")

      And("the intermediary selects no on adds another registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("yes")

      And("the intermediary selects Croatia on the first eu-fixed-establishment-country page")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1")
      registration.selectCountry("Croatia")

      And("the intermediary enters the fixed establishment details on the eu-fixed-establishment-address page")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Croatia Trading")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Split", "", "HR 65487")

      And("the intermediary selects VAT number on the first registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")

      And("the intermediary adds the VAT registration number for Croatia")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("HR11114567888")

      And("the intermediary changes the Tax registration type from VAT number to Tax identification number")
      registration.checkJourneyUrl("check-tax-details")
      registration.selectChangeOrRemoveLink("registration-tax-type\\/1\\?waypoints\\=check-tax-details-1")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("tax id number")

      And("the intermediary adds the tax identification number for Croatia")
      registration.checkJourneyUrl("eu-tax-identification-number/1")
      registration.enterAnswer("HR 123 456")

      And("the intermediary changes the Business trading name")
      registration.checkJourneyUrl("check-tax-details")
      registration.selectChangeOrRemoveLink("eu-fixed-establishment-address\\/1\\?waypoints\\=check-tax-details-1")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Croatia Trading New")
      registration.continue()

      And("the intermediary selects continue on the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()

      And("the intermediary selects yes on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("yes")

      And("the intermediary selects Slovenia on the second eu-fixed-establishment-country page")
      registration.checkJourneyUrl("eu-fixed-establishment-country/2")
      registration.selectCountry("Slovenia")

      And("the intermediary enters the fixed establishment details on the eu-fixed-establishment-address page")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")
      registration.enterFETradingName("Slovenia Goods 55")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Koper", "", "")

      And("the intermediary selects tax id number on the second registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/2")
      registration.selectRegistrationType("tax id number")

      And("the intermediary adds the tax identification number for Slovenia")
      registration.checkJourneyUrl("eu-tax-identification-number/2")
      registration.enterAnswer("SLOV 123 456")

      And("the intermediary selects continue on the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()

      And("the intermediary clicks remove on the add-tax-details page for second EU registration")
      registration.checkJourneyUrl("add-tax-details")
      registration.selectChangeOrRemoveLink("remove-tax-details\\/2")

      And("the intermediary selects yes on the remove-tax-details page")
      registration.checkJourneyUrl("remove-tax-details/2")
      registration.answerRadioButton("yes")

      Then("the intermediary selects yes on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("yes")

      And(
        "the intermediary selects which country their fixed establishment is in on the eu-fixed-establishment-country page"
      )
      registration.checkJourneyUrl("eu-fixed-establishment-country/2")
      registration.selectCountry("Estonia")

      And("the intermediary enters the fixed establishment details on the eu-fixed-establishment-address page")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")
      registration.enterFETradingName("Estonian Trading Name")
      registration.enterFixedEstablishmentAddress("55 Test Avenue", "", "City", "", "E12541")

      And("the intermediary selects the Tax ID number registration type on the registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/2")
      registration.selectRegistrationType("tax id number")

      And("the intermediary enters the Tax ID number on the eu-tax-identification-number page")
      registration.checkJourneyUrl("eu-tax-identification-number/2")
      registration.enterAnswer("E123S123B")

      And("the intermediary continues through the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details/2")
      registration.continue()

      When(
        "the intermediary selects change for Greece on the add-tax-details page"
      )
      registration.checkJourneyUrl("add-tax-details")
      registration.selectChangeOrRemoveLink("check-tax-details\\/1\\?waypoints\\=change-add-tax-details")

      And(
        "the intermediary selects change for Country on the check-tax-details page"
      )
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-add-tax-details")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment-country\\/1\\?waypoints\\=check-tax-details-1\\%2Cchange-add-tax-details"
      )

      And(
        "the intermediary selects a different country on the vat-registered-eu-country page"
      )
      registration.checkJourneyUrl("eu-fixed-establishment-country/1")
      registration.clearCountry()
      registration.selectCountry("France")

      And("the intermediary enters the fixed establishment details on the eu-fixed-establishment-address page")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("French Trading Name")
      registration.enterFixedEstablishmentAddress("123 Street Name", "", "Town", "", "FR12345")

      And("the intermediary selects the Tax ID number registration type on the registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("tax id number")

      And("the intermediary enters the Tax ID number on the eu-tax-identification-number page")
      registration.checkJourneyUrl("eu-tax-identification-number/1")
      registration.enterAnswer("F121212FR")

      And("the intermediary changes the Tax registration type from VAT number to Tax identification number")
      registration.checkJourneyUrl("check-tax-details")
      registration.selectChangeOrRemoveLink("registration-tax-type\\/1\\?waypoints\\=check-tax-details-1")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")

      And("the intermediary adds the VAT registration number for Croatia")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("FRBB999888777")

      And("the intermediary continues through the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details/1")
      registration.continue()

      And("the intermediary selects no on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")

      Then("the intermediary enters on Contact-details page")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Example", "24242424234", "test-name@email.co.uk")

      Then("the intermediary completes the email verification process")
      email.completeEmailVerification("registration")

      Then("the intermediary enters bank or building society account details on bank-account-details page")
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      When("the intermediary submits the registration on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()

      Then("the intermediary is on the successful submission page")
      registration.checkJourneyUrl("successful")

    }

    Scenario("Using change links on check-your-answers page to change answers in a registration") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      And("the intermediary adds all of the initial registration answers")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-other-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Example", "24242424234", "test-name@email.co.uk")
      email.completeEmailVerification("registration")
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      Then("the intermediary adds a trading name")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink("have-other-trading-name\\?waypoints\\=check-your-answers")
      registration.checkJourneyUrl("have-other-trading-name?waypoints=check-your-answers")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("other-trading-name/1")
      registration.enterAnswer("new trading-name")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=check-your-answers")
      registration.answerRadioButton("no")

      Then("the intermediary adds a previous registration")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "has-previously-registered-as-intermediary\\?waypoints\\=check-your-answers"
      )
      registration.checkJourneyUrl("has-previously-registered-as-intermediary?waypoints=check-your-answers")
      registration.answerRadioButton("yes")
      Then("the intermediary selects which country was it registered in on previous eu country page")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Czech Republic")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN2031452368")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=check-your-answers")
      registration.answerRadioButton("no")

      Then("the intermediary adds some tax details")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=check-your-answers"
      )
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=check-your-answers")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1")
      registration.selectCountry("Spain")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Barcelona Trading")
      registration.enterFixedEstablishmentAddress("500 La Rambla", "", "Barcelona", "", "08002")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("ESX1234567X")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")

      Then("the intermediary changes some of their contact details")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "contact-details\\?waypoints\\=check-your-answers"
      )
      registration.checkJourneyUrl("contact-details?waypoints=check-your-answers")
      registration.updateField("fullName", "New Test Name")
      registration.continue()

      Then("the intermediary changes some of their bank details")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "bank-account-details\\?waypoints\\=check-your-answers"
      )
      registration.checkJourneyUrl("bank-account-details?waypoints=check-your-answers")
      registration.updateField("iban", "GB91BKEN10000041610008")
      registration.continue()

      Then("the intermediary successfully submits their registration")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()
      registration.checkJourneyUrl("successful")
    }

    Scenario("Add extra answers to registration via the check-your-answers page") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      And("the intermediary adds all of the initial registration answers")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-other-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("other-trading-name/1")
      registration.enterAnswer("first trading name")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Austria")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN0401234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1")
      registration.selectCountry("Denmark")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Danish Company")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Copenhagen", "", "DK123456")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("DK12344321")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Example", "24242424234", "test-name@email.co.uk")
      email.completeEmailVerification("registration")
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      Then("the intermediary adds another trading name")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink("add-other-trading-name\\?waypoints\\=check-your-answers")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=check-your-answers")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("other-trading-name/2")
      registration.enterAnswer("another trading-name")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=check-your-answers")
      registration.answerRadioButton("no")

      Then("the intermediary adds another previous registration")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "add-previous-intermediary-registration\\?waypoints\\=check-your-answers"
      )
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=check-your-answers")
      registration.answerRadioButton("yes")
      Then("the intermediary selects which country was it registered in on previous eu country page")
      registration.checkJourneyUrl("previous-eu-country/2")
      registration.selectCountry("France")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2")
      registration.enterAnswer("IN2508747856")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=check-your-answers")
      registration.answerRadioButton("no")

      Then("the intermediary adds more tax details")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "add-tax-details\\?waypoints\\=check-your-answers"
      )
      registration.checkJourneyUrl("add-tax-details?waypoints=check-your-answers")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-fixed-establishment-country/2")
      registration.selectCountry("Italy")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")
      registration.enterFETradingName("Roma Trading")
      registration.enterFixedEstablishmentAddress("12 Piazza del Popolo", "", "Rome", "", "00187")
      registration.checkJourneyUrl("registration-tax-type/2")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/2")
      registration.enterAnswer("ITA8888")
      registration.checkJourneyUrl("check-tax-details/2")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")

      Then("the intermediary changes some of their contact details")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "contact-details\\?waypoints\\=check-your-answers"
      )
      registration.checkJourneyUrl("contact-details?waypoints=check-your-answers")
      registration.updateField("telephoneNumber", "+447778889991")
      registration.continue()

      Then("the intermediary changes some of their bank details")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "bank-account-details\\?waypoints\\=check-your-answers"
      )
      registration.checkJourneyUrl("bank-account-details?waypoints=check-your-answers")
      registration.updateField("accountName", "New Bank-Account Name")
      registration.continue()

      Then("the intermediary successfully submits their registration")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()
      registration.checkJourneyUrl("successful")
    }

    Scenario("Intermediary removes all trading names via remove-all-trading-names page") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      And("the intermediary adds answers all the way through to the check-your-answers page")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-other-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("other-trading-name/1")
      registration.enterAnswer("first trading name")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("other-trading-name/2")
      registration.enterAnswer("trading 2!")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Testname", "12345678", "test@email.com")
      email.completeEmailVerification("registration")
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      When("the intermediary selects change for Have a different UK trading name on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink("have-other-trading-name\\?waypoints\\=check-your-answers")

      And("the intermediary changes the answer to no on the have-other-trading-name page")
      registration.checkJourneyUrl("have-other-trading-name?waypoints=check-your-answers")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the remove-all-trading-names page")
      registration.checkJourneyUrl("remove-all-trading-names?waypoints=check-your-answers")
      registration.answerRadioButton("yes")

      And("the intermediary submits their registration successfully")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()
      registration.checkJourneyUrl("successful")
    }

    Scenario(
      "Intermediary removes all previous registrations via remove-all-previous-intermediary-registrations page"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      And("the intermediary adds answers all the way through to the check-your-answers page")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-other-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Austria")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN0401234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/2")
      registration.selectCountry("Belgium")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2")
      registration.enterAnswer("IN0561234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Testname", "12345678", "test@email.com")
      email.completeEmailVerification("registration")
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      When(
        "the intermediary selects change for Other IOSS intermediary registrations on the check-your-answers page"
      )
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "has-previously-registered-as-intermediary\\?waypoints\\=check-your-answers"
      )

      And("the intermediary changes the answer to no on the has-previously-registered-as-intermediary page")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary?waypoints=check-your-answers")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the remove-all-trading-names page")
      registration.checkJourneyUrl("remove-all-previous-intermediary-registrations?waypoints=check-your-answers")
      registration.answerRadioButton("yes")

      And("the intermediary submits their registration successfully")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()
      registration.checkJourneyUrl("successful")
    }

    Scenario(
      "Intermediary removes all tax details via remove-all-tax-details page"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      And("the intermediary adds answers all the way through to the check-your-answers page")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-other-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1")
      registration.selectCountry("Denmark")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Danish Company")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Copenhagen", "", "DK123456")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("DK12344321")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-fixed-establishment-country/2")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")
      registration.enterFETradingName("Slovenia Goods 55")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Koper", "", "")
      registration.checkJourneyUrl("registration-tax-type/2")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/2")
      registration.enterAnswer("SLOV 123 456")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Testname", "12345678", "test@email.com")
      email.completeEmailVerification("registration")
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      When(
        "the intermediary selects change for Registered for tax in EU countries on the check-your-answers page"
      )
      registration.checkJourneyUrl("check-your-answers")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=check-your-answers"
      )

      And("the intermediary changes the answer to no on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=check-your-answers")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the remove-all-tax-details page")
      registration.checkJourneyUrl("remove-all-tax-details?waypoints=check-your-answers")
      registration.answerRadioButton("yes")

      And("the intermediary submits their registration successfully")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()
      registration.checkJourneyUrl("successful")
    }
  }
}
