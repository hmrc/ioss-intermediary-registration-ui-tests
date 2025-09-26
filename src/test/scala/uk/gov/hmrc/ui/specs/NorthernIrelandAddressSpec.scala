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

class NorthernIrelandAddressSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification

  Feature("Northern Ireland Address journeys") {

    Scenario("An intermediary registered outside of NI cannot access the service") {

      Given(
        "the intermediary accesses the IOSS Intermediary Registration Service with a non-NI postcode in VAT details"
      )
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000005", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("there is no Principal Place of Business Address displayed")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.checkPrincipalPlaceOfBusiness(false)

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.answerVatDetailsChoice("Yes")

      When("the intermediary enters a non-NI postcode on the ni-address page")
      registration.checkJourneyUrl("ni-address")
      registration.enterNiAddress("1 Street Name", "", "Non-NI city", "", "AB1 1AB")

      Then("the intermediary is on the cannot-register-not-ni-based-business page")
      registration.checkJourneyUrl("cannot-register-not-ni-based-business")
    }

    Scenario(
      "An intermediary with a non-NI postcode in VAT details can enter an NI postcode after confirm-vat-details page"
    ) {

      Given(
        "the intermediary accesses the IOSS Intermediary Registration Service with a non-NI postcode in VAT details"
      )
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000005", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.checkPrincipalPlaceOfBusiness(false)
      registration.answerVatDetailsChoice("Yes")

      When("the intermediary enters an NI postcode on the ni-address page")
      registration.checkJourneyUrl("ni-address")
      registration.enterNiAddress("123 Street Name", "", "Belfast", "", "BT1 1AB")

      And("the intermediary selects no on the have-uk-trading-name page")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")

      Then("the intermediary selects on no if ever registered as an IOSS scheme in an Eu country")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")

      And("the intermediary continues through the registration journey")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Example", "24242424234", "test-name@email.co.uk")
      email.completeEmailVerification()
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      When("the Northern Ireland Address is displayed on the check-your-answers page ")
      registration.checkJourneyUrl("check-your-answers")
      registration.checkNiAddressOnCya(true)
      registration.checkPrincipalPlaceOfBusiness(false)

      Then("the intermediary can successfully submit their registration")
      registration.submit()
      registration.checkJourneyUrl("successful")
    }

    Scenario(
      "An intermediary with an NI postcode entered via ni-address changes to non-NI postcode via check-your-answers"
    ) {

      Given(
        "the intermediary accesses the IOSS Intermediary Registration Service with a non-NI postcode in VAT details"
      )
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000005", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      When("the intermediary enters an NI postcode on the ni-address page")
      registration.checkJourneyUrl("ni-address")
      registration.enterNiAddress("123 Street Name", "", "Belfast", "", "BT1 1AB")

      And("the intermediary continues through the registration journey")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Example", "24242424234", "test-name@email.co.uk")
      email.completeEmailVerification()
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      When("the Northern Ireland Address is displayed on the check-your-answers page ")
      registration.checkJourneyUrl("check-your-answers")
      registration.checkNiAddressOnCya(true)

      Then("the intermediary changes their NI address to a non-NI address")
      registration.selectChangeOrRemoveLink("ni-address\\?waypoints\\=check-your-answers")
      registration.checkJourneyUrl("ni-address?waypoints=check-your-answers")
      registration.enterNiAddress("123 Street Name", "", "Non-NI city", "", "AB1 1AB")

      Then("the intermediary is on the cannot-register-not-ni-based-business page")
      registration.checkJourneyUrl("cannot-register-not-ni-based-business")
    }

    Scenario(
      "An intermediary VAT registered within Northern Ireland has the correct address labels in their registration"
    ) {

      Given(
        "the intermediary accesses the IOSS Intermediary Registration Service with a non-NI postcode in VAT details"
      )
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the Principal Place of Business Address displayed in the NI address from their VAT details")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.checkPrincipalPlaceOfBusiness(true)

      When("the intermediary selects yes on the confirm-vat-details page")
      registration.answerVatDetailsChoice("Yes")

      And("the intermediary continues through the registration journey")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Example", "24242424234", "test-name@email.co.uk")
      email.completeEmailVerification()
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      And(
        "the Principal Place of Business is displayed on the check-your-answers page and no separate Northern Ireland address"
      )
      registration.checkJourneyUrl("check-your-answers")
      registration.checkPrincipalPlaceOfBusiness(true)
      registration.checkNiAddressOnCya(false)

      Then("the intermediary successfully submits their registration")
      registration.submit()
      registration.checkJourneyUrl("successful")
    }

    Scenario(
      "Intermediary with NI address saves registration and returns later with a non-NI postcode in their VAT details"
    ) {

      Given("the intermediary logs into the service with a previously saved registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the continue-registration page")
      registration.checkJourneyUrl("continue-registration")

      And("the intermediary selects yes to continue with their saved registration")
      registration.clickLink("continueProgress")
      registration.continue()

      When("the intermediary completes the rest of the registration")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Example", "24242424234", "test-name@email.co.uk")
      email.completeEmailVerification()
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      Then("the intermediary will be required to provide a new address via the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()

      And("the intermediary enters an NI postcode on the ni-address page")
      registration.checkJourneyUrl("ni-address")
      registration.enterNiAddress("123 Street Name", "", "Belfast", "", "BT1 1AB")

      When("the intermediary submits the registration on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()

      Then("the intermediary is on the successful submission page")
      registration.checkJourneyUrl("successful")
    }

    Scenario(
      "Intermediary with NI address saves registration and returns later with a non-NI postcode in their VAT details and cannot progress without an NI address"
    ) {

      Given("the intermediary logs into the service with a previously saved registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the continue-registration page")
      registration.checkJourneyUrl("continue-registration")

      And("the intermediary selects yes to continue with their saved registration")
      registration.clickLink("continueProgress")
      registration.continue()

      When("the intermediary completes the rest of the registration")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("contact-details")
      registration.fillContactDetails("Example", "24242424234", "test-name@email.co.uk")
      email.completeEmailVerification()
      registration.checkJourneyUrl("bank-account-details")
      registration.fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")

      Then("the intermediary will be required to provide a new address via the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")
      registration.submit()

      And("the intermediary enters a non-NI postcode on the ni-address page")
      registration.checkJourneyUrl("ni-address")
      registration.enterNiAddress("123 Street Name", "", "Town", "", "AA1 1AA")

      Then("the intermediary is on the cannot-register-not-ni-based-business page")
      registration.checkJourneyUrl("cannot-register-not-ni-based-business")
    }

    Scenario(
      "Intermediary with manually entered NI address has the correct sections in the change-your-registration page for amend registration"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "amendNIManual", "amend")
      registration.checkJourneyUrl("change-your-registration")

      Then("the intermediary's Principal place of business address is displayed within the VAT details section")
      registration.checkPrincipalPlaceOfBusiness(false)

      And("the manually entered NI address field is not displayed")
      registration.checkNiAddressOnCya(true)
    }

    Scenario(
      "Intermediary with NI address retrieved in VAT info has the correct sections in the change-your-registration page for amend registration"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossInt", "amend")
      registration.checkJourneyUrl("change-your-registration")

      Then("the intermediary's Principal place of business address is displayed within the VAT details section")
      registration.checkPrincipalPlaceOfBusiness(true)

      And("the manually entered NI address field is not displayed")
      registration.checkNiAddressOnCya(false)
    }
  }
}
