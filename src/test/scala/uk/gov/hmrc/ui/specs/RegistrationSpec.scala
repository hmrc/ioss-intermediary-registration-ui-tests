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

class RegistrationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification

  Feature("Registration journeys") {

    Scenario("Intermediary registers using the IOSS Intermediary Registration Service - full answers") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary selects no on the ioss-intermediary-registered page")
      registration.answerRadioButton("no")

      And("the intermediary selects yes on the registered-for-vat-in-uk page")
      registration.checkJourneyUrl("registered-for-vat-in-uk")
      registration.answerRadioButton("yes")

      And("the intermediary selects yes on the ni-or-eu-based page")
      registration.checkJourneyUrl("ni-or-eu-based")
      registration.answerRadioButton("yes")

      Then("the intermediary presses continue on the register-to-use-service page")
      registration.checkJourneyUrl("register-to-use-service")
      registration.continue()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      And("the intermediary selects yes on the have-other-trading-name page")
      registration.checkJourneyUrl("have-other-trading-name")
      registration.answerRadioButton("yes")

      And("the intermediary adds the first trading name")
      registration.checkJourneyUrl("other-trading-name/1")
      registration.enterAnswer("first trading name")

      And("the intermediary selects yes on the add-other-trading-name page")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("yes")

      And("the intermediary adds the second trading name")
      registration.checkJourneyUrl("other-trading-name/2")
      registration.enterAnswer("trading 2!")

      And("the intermediary selects no on the add-other-trading-name page")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("no")

      Then("the intermediary selects on yes if ever registered as an IOSS scheme in an Eu country")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")

      Then("the intermediary selects which country was it registered in on previous eu country page")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Austria")

      Then(
        "the intermediary clicks and enters registration number for Austria on previous-intermediary-registration-number page"
      )
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN0401234567")

      Then("the intermediary checks you added details for one country on add-previous-intermediary-registration page")
      registration.checkJourneyUrl("add-previous-intermediary-registration")

      And("the intermediary selects yes to add another registration on add-previous-intermediary-registration page")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("yes")

      Then("the intermediary selects which country was it registered in on previous eu country page")
      registration.checkJourneyUrl("previous-eu-country/2")
      registration.selectCountry("Belgium")

      Then(
        "the intermediary clicks and enters registration number for Austria on previous-intermediary-registration-number/2 page"
      )
      registration.checkJourneyUrl("previous-intermediary-registration-number/2")
      registration.enterAnswer("IN0561234567")

      And(
        "the intermediary checks added registration details for 2 countries on add-previous-intermediary-registration page"
      )
      registration.checkJourneyUrl("add-previous-intermediary-registration")

      And("the intermediary selects no to add another registration on add-previous-intermediary-registration page")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("no")

      Then("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("yes")

      And(
        "the intermediary selects which country their fixed establishment is in on the eu-fixed-establishment-country page"
      )
      registration.checkJourneyUrl("eu-fixed-establishment-country/1")
      registration.selectCountry("Denmark")

      And("the intermediary enters the fixed establishment details on the eu-fixed-establishment-address page")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Danish Company")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Copenhagen", "", "DK123456")

      And("the intermediary selects the VAT Number registration type on the registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")

      And("the intermediary enters the VAT number on the eu-vat-number page")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("DK12344321")

      And("the intermediary continues through the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details/1")
      registration.continue()

      Then("the intermediary selects yes on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("yes")

      And(
        "the intermediary selects which country their fixed establishment is in on the eu-fixed-establishment-country page"
      )
      registration.checkJourneyUrl("eu-fixed-establishment-country/2")
      registration.selectCountry("Slovenia")

      And("the intermediary enters the fixed establishment details on the eu-fixed-establishment-address page")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")
      registration.enterFETradingName("Slovenia Goods 55")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Koper", "", "")

      And("the intermediary selects the Tax ID number registration type on the registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/2")
      registration.selectRegistrationType("tax id number")

      And("the intermediary enters the Tax ID number on the eu-tax-identification-number page")
      registration.checkJourneyUrl("eu-tax-identification-number/2")
      registration.enterAnswer("SLOV 123 456")

      And("the intermediary continues through the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details/2")
      registration.continue()

      Then("the intermediary selects no on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")

      Then("the intermediary enters credentials on Contact-details page")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Testname", "12345678", "test@email.com")

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
//      IOSS number is currently hardcoded - may want to do further checks once API is developed

    }

    Scenario("Intermediary registers using the IOSS Intermediary Registration Service - minimal answers") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary selects no on the ioss-intermediary-registered page")
      registration.answerRadioButton("no")

      And("the intermediary selects yes on the registered-for-vat-in-uk page")
      registration.checkJourneyUrl("registered-for-vat-in-uk")
      registration.answerRadioButton("yes")

      And("the intermediary selects yes on the ni-or-eu-based page")
      registration.checkJourneyUrl("ni-or-eu-based")
      registration.answerRadioButton("yes")

      Then("the intermediary presses continue on the register-to-use-service page")
      registration.checkJourneyUrl("register-to-use-service")
      registration.continue()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      And("the intermediary selects no on the have-other-trading-name page")
      registration.checkJourneyUrl("have-other-trading-name")
      registration.answerRadioButton("no")

      Then("the intermediary selects on no if ever registered as an IOSS scheme in an Eu country")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")

      Then("the intermediary selects no on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment")
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
      //      IOSS number is currently hardcoded - may want to do further checks once API is developed

    }
  }
}
