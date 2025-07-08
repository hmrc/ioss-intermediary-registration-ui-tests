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
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "vatOnly")
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
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkPrincipalPlaceOfBusiness(false)
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      When("the intermediary enters a non-NI poscode on the ni-address page")
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
      auth.loginUsingAuthorityWizard("700000003", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary navigates through the filter question pages")
      registration.initialSteps()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      When("the intermediary enters a non-NI poscode on the ni-address page")
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
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
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
  }
}
