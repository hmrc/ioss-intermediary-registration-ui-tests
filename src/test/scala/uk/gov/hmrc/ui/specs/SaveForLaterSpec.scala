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

class SaveForLaterSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification

  Feature("Save and come back later journeys") {

    Scenario("Intermediary can save their registration progress and return to the last page they were on") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary answers the filter questions")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("registered-for-vat-in-uk")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("ni-or-eu-based")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("register-to-use-service")
      registration.continue()

      Then("the intermediary adds data for the trading names section in the journey")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("uk-trading-name/1")
      registration.enterAnswer("first saved trading name")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("uk-trading-name/2")
      registration.enterAnswer("second saved trading name")
      registration.checkJourneyUrl("add-uk-trading-name")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      Then("the intermediary is back to the registration journey and selects no on the add-uk-trading-name page")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.answerRadioButton("no")

      Then("the intermediary adds previous registration data to their registration")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Austria")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN0401234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/2")
      registration.selectCountry("Belgium")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      Then(
        "the intermediary is back to the registration journey and  enters registration number for Austria on previous-intermediary-registration-number/2 page"
      )
      registration.checkJourneyUrl("previous-intermediary-registration-number/2")
      registration.enterAnswer("IN0561234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      Then("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("yes")

      And(
        "the intermediary enters some fixed establishment data to their registration"
      )
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Denmark")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Danish Company")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      Then("the intermediary continues to add fixed establishment data")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Danish Company")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Copenhagen", "", "DK123456")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      And("the intermediary continues to add further fixed establishment data")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("DK12344321")
      registration.checkJourneyUrl("check-tax-details/1")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/2")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")
      registration.enterFETradingName("Slovenia Goods 55")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Koper", "", "")
      registration.checkJourneyUrl("registration-tax-type/2")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/2")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      And("the intermediary completes the information for their second fixed establishment")
      registration.checkJourneyUrl("eu-tax-identification-number/2")
      registration.enterAnswer("SLOV 123 456")
      registration.checkJourneyUrl("check-tax-details/2")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      Then("the intermediary enters credentials on Contact-details page")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Testname", "12345678", "test@email.com")

      Then("the intermediary completes the email verification process")
      email.completeEmailVerification("registration")
      registration.checkJourneyUrl("bank-account-details")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      Then("the intermediary enters bank or building society account details on bank-account-details page")
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")
      registration.checkJourneyUrl("check-your-answers")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the continue to complete your registration link")
      registration.clickLink("continueToYourRegistration")

      When("the intermediary submits the registration on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()

      Then("the intermediary is on the successful submission page")
      registration.checkJourneyUrl("successful")
    }

    Scenario("Intermediary can access their saved registration via their government gateway login and complete it") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary completes some of their registration details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("registered-for-vat-in-uk")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("ni-or-eu-based")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("register-to-use-service")
      registration.continue()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("uk-trading-name/1")
      registration.enterAnswer("first trading name")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("uk-trading-name/2")
      registration.enterAnswer("trading 2!")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Austria")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN0401234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/2")
      registration.selectCountry("Belgium")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2")
      registration.enterAnswer("IN0561234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Denmark")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Danish Company")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Copenhagen", "", "DK123456")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the sign out and come back later link")
      registration.clickLink("signOut")

      Given("the intermediary logs into IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the continue-registration page")
      registration.checkJourneyUrl("continue-registration")

      And("the intermediary selects yes to continue with their saved registration")
      registration.clickLink("continueProgress")
      registration.continue()

      And("the intermediary can continue their registration from the eu-vat-number page")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("DK12344321")
      registration.checkJourneyUrl("check-tax-details/1")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/2")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")
      registration.enterFETradingName("Slovenia Goods 55")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Koper", "", "")
      registration.checkJourneyUrl("registration-tax-type/2")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/2")
      registration.enterAnswer("SLOV 123 456")
      registration.checkJourneyUrl("check-tax-details/2")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Testname", "12345678", "test@email.com")
      email.completeEmailVerification("registration")
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      When("the intermediary submits the registration on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()

      Then("the intermediary is on the successful submission page")
      registration.checkJourneyUrl("successful")
    }

    Scenario(
      "Intermediary can access their saved registration via their government gateway login, delete it and start again"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary completes some of their registration details")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("registered-for-vat-in-uk")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("ni-or-eu-based")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("register-to-use-service")
      registration.continue()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("uk-trading-name/1")
      registration.enterAnswer("first trading name")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("uk-trading-name/2")
      registration.enterAnswer("trading 2!")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Austria")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN0401234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/2")
      registration.selectCountry("Belgium")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2")
      registration.enterAnswer("IN0561234567")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.checkJourneyUrl("add-previous-intermediary-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Denmark")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFETradingName("Danish Company")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Copenhagen", "", "DK123456")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("DK12344321")
      registration.checkJourneyUrl("check-tax-details/1")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/2")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")

      When("the intermediary selects the save and come back later button")
      registration.saveAndComeBackLater()

      Then("the intermediary is on the progress saved page")
      registration.checkJourneyUrl("progress-saved")

      When("the intermediary selects the sign out and come back later link")
      registration.clickLink("signOut")

      Given("the intermediary logs into IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the continue-registration page")
      registration.checkJourneyUrl("continue-registration")

      And("the intermediary selects yes to continue with their saved registration")
      registration.clickLink("deleteProgress")
      registration.continue()

      And("the intermediary can start their registration from scratch and submit it successfully")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.submitMinimalRegistration()
    }

    Scenario("Intermediary attempts to access saved registration journey but does not have a saved registration") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service via the saved registration endpoint")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the no-registration-in-progress page")
      registration.checkJourneyUrl("no-registration-in-progress")
    }
  }
}
