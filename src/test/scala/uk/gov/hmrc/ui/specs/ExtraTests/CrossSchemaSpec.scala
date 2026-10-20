/*
 * Copyright 2026 HM Revenue & Customs
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

import uk.gov.hmrc.ui.pages.{Auth, CrossSchema, EmailVerification, Registration}
import uk.gov.hmrc.ui.specs.BaseSpec

class CrossSchemaSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification
  private val crossSchema  = CrossSchema

  Feature("Cross schema journeys") {

    Scenario("Registration for trader with an OSS registration - makes changes") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("300000002", "Organisation", "crossSchemaOss", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      And("the intermediary answers the initial questions")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      When("the intermediary is at the trading names section")
      registration.checkJourneyUrl("add-other-trading-name")

      Then(
        "they are shown their trading names from their OSS registration and advised any changes will be made to those as well"
      )
      crossSchema.checkTradingNames("plural")
      crossSchema.checkHintTextAndWarnings("registration", true, "trading names")

      And("the intermediary removes a trading name")
      registration.selectChangeOrRemoveLink("remove-other-trading-name\\/2")
      registration.checkJourneyUrl("remove-other-trading-name/2")
      registration.answerRadioButton("yes")

      And("the intermediary adds a new trading name")
      registration.checkJourneyUrl("add-other-trading-name")
      crossSchema.checkTradingNames("singular")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("other-trading-name/2")
      registration.enterAnswer("A cross schema trading name")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("no")

      And("the intermediary answers no on the next few sections")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")

      When("the intermediary is on the contact-details page")
      registration.checkJourneyUrl("contact-details")

      Then(
        "the contact details are already populated from their OSS registration and the correct warnings and hint text are shown"
      )
      crossSchema.checkHintTextAndWarnings("registration", true, "contact details")

      And("the intermediary amends their name")
      registration.updateField("fullName", "New Name")
      registration.continue()
      email.completeEmailVerification("registration")

      When("the intermediary is on the bank-account-details page")
      registration.checkJourneyUrl("bank-account-details")

      Then(
        "the bank details are already populated from their OSS registration and the correct warnings and hint text are shown"
      )
      crossSchema.checkHintTextAndWarnings("registration", true, "bank details")

      And("the intermediary updates some of their bank details")
      registration.updateField("bic", "")
      registration.updateField("iban", "GB91BKEN10000041610008")
      registration.continue()

      When("the intermediary is on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")

      And("the intermediary submits their registration")
      registration.submit()

      Then(
        "the intermediary is shown the acknowledgment page with the correct text regarding updating other registrations"
      )
      registration.checkJourneyUrl("successful")
      crossSchema.checkConfirmation(true)
    }

    Scenario("Registration for trader with an OSS registration - does not make changes") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("300000002", "Organisation", "crossSchemaOss", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      And("the intermediary answers the initial questions")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      And("the intermediary does not add a new trading name")
      registration.checkJourneyUrl("add-other-trading-name")
      crossSchema.checkTradingNames("plural")
      registration.answerRadioButton("no")

      And("the intermediary answers no on the next few sections")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")

      When("the intermediary is on the contact-details page")
      registration.checkJourneyUrl("contact-details")

      Then(
        "the contact details are already populated from their OSS registration and the correct warnings and hint text are shown"
      )
      crossSchema.checkHintTextAndWarnings("registration", true, "contact details")

      And("the intermediary continues without updating any of the contact details")
      registration.continue()
      email.completeEmailVerification("registration")

      When("the intermediary is on the bank-account-details page")
      registration.checkJourneyUrl("bank-account-details")

      Then(
        "the bank details are already populated from their OSS registration and the correct warnings and hint text are shown"
      )
      crossSchema.checkHintTextAndWarnings("registration", true, "bank details")

      And("the intermediary continues without updating any of the bank details")
      registration.continue()

      When("the intermediary is on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")

      And("the intermediary submits their registration")
      registration.submit()

      Then(
        "the intermediary is shown the acknowledgment page with the correct text regarding updating other registrations"
      )
      registration.checkJourneyUrl("successful")
      crossSchema.checkConfirmation(false)
    }

    Scenario("Registration for trader with both OSS and IOSS registrations - makes changes") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("300000002", "Organisation", "crossSchemaOssAndIoss", "registration")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      And("the intermediary answers the initial questions")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      When("the intermediary is at the trading names section")
      registration.checkJourneyUrl("add-other-trading-name")

      Then(
        "they are shown their trading names from their OSS registration and advised any changes will be made to those as well"
      )
      crossSchema.checkTradingNames("plural")
      crossSchema.checkHintTextAndWarnings("registration", true, "trading names")

      And("the intermediary removes a trading name")
      registration.selectChangeOrRemoveLink("remove-other-trading-name\\/1")
      registration.checkJourneyUrl("remove-other-trading-name/1")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("add-other-trading-name")
      registration.answerRadioButton("no")

      And("the intermediary answers no on the next few sections")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("eu-fixed-establishment")
      registration.answerRadioButton("no")

      When("the intermediary is on the contact-details page")
      registration.checkJourneyUrl("contact-details")

      Then(
        "the contact details are already populated from their OSS registration and the correct warnings and hint text are shown"
      )
      crossSchema.checkHintTextAndWarnings("registration", true, "contact details")

      And("the intermediary amends their name")
      registration.updateField("fullName", "New Name")
      registration.continue()
      email.completeEmailVerification("registration")

      When("the intermediary is on the bank-account-details page")
      registration.checkJourneyUrl("bank-account-details")

      Then(
        "the bank details are already populated from their OSS registration and the correct warnings and hint text are shown"
      )
      crossSchema.checkHintTextAndWarnings("registration", true, "bank details")

      And("the intermediary updates some of their bank details")
      registration.updateField("bic", "")
      registration.updateField("iban", "GB91BKEN10000041610008")
      registration.continue()

      When("the intermediary is on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")

      And("the intermediary submits their registration")
      registration.submit()

      Then(
        "the intermediary is shown the acknowledgment page with the correct text regarding updating other registrations"
      )
      registration.checkJourneyUrl("successful")
      crossSchema.checkConfirmation(true)
    }

//    Bug VEI-964 is blocking this
//    Scenario("Registration for trader with previous IOSS registrations - makes changes") {
//
//      Given("the intermediary accesses the IOSS Intermediary Registration Service")
//      auth.goToAuthorityWizard()
//      auth.loginUsingAuthorityWizard("100000001", "Organisation", "crossSchemaMultipleIOSS", "registration")
//      registration.checkJourneyUrl("ioss-intermediary-registered")
//
//      And("the intermediary answers the initial questions")
//      registration.initialSteps()
//      registration.checkJourneyUrl("confirm-vat-details")
//      registration.answerVatDetailsChoice("Yes")
//
//      When("the intermediary is at the trading names section")
//      registration.checkJourneyUrl("add-other-trading-name")
//
//      Then(
//        "they are shown their trading names from their OSS registration and advised any changes will be made to those as well"
//      )
//      crossSchema.checkTradingNames("plural")
//      crossSchema.checkHintTextAndWarnings("registration", true, "trading names")
//
//      And("the intermediary removes a trading name")
//      registration.selectChangeOrRemoveLink("remove-other-trading-name\\/1")
//      registration.checkJourneyUrl("remove-other-trading-name/1")
//      registration.answerRadioButton("yes")
//      registration.checkJourneyUrl("add-other-trading-name")
//      registration.answerRadioButton("no")
//
//      And("the intermediary answers no on the next few sections")
//      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
//      registration.answerRadioButton("no")
//      registration.checkJourneyUrl("eu-fixed-establishment")
//      registration.answerRadioButton("no")
//
//      When("the intermediary is on the contact-details page")
//      registration.checkJourneyUrl("contact-details")
//
//      Then(
//        "the contact details are already populated from their OSS registration and the correct warnings and hint text are shown"
//      )
//      crossSchema.checkHintTextAndWarnings("registration", true, "contact details")
//
//      And("the intermediary amends their name")
//      registration.updateField("fullName", "New Name")
//      registration.continue()
//      email.completeEmailVerification("registration")
//
//      When("the intermediary is on the bank-account-details page")
//      registration.checkJourneyUrl("bank-account-details")
//
//      Then(
//        "the bank details are already populated from their OSS registration and the correct warnings and hint text are shown"
//      )
//      crossSchema.checkHintTextAndWarnings("registration", true, "bank details")
//
//      And("the intermediary updates some of their bank details")
//      registration.updateField("bic", "")
//      registration.updateField("iban", "GB91BKEN10000041610008")
//      registration.continue()
//
//      When("the intermediary is on the check-your-answers page")
//      registration.checkJourneyUrl("check-your-answers")
//
//      And("the intermediary submits their registration")
//      registration.submit()
//
//      Then(
//        "the intermediary is shown the acknowledgment page with the correct text regarding updating other registrations"
//      )
//      registration.checkJourneyUrl("successful")
//      crossSchema.checkConfirmation(true)
//    }

    Scenario(
      "Intermediary with a previous IOSS registration has the correct text warnings in amend registration"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "crossSchemaAmendIoss", "amend")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")

      // this is incorrect as trading names should be pulling through from IOSS
      When(
        "there are currently no trading names and the intermediary selects the change link for Is your only trading name"
      )
      registration.selectChangeOrRemoveLink("have-no-other-trading-names\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("have-no-other-trading-names?waypoints=change-your-registration")

      Then("the intermediary adds a trading name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("other-trading-name/1?waypoints=add-other-trading-name%2Cchange-your-registration")
      registration.enterAnswer("cross schema")

      And("the only trading name displayed is the one just added and the correct warnings/hint text are on the page")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=change-your-registration")
//      heading check
      crossSchema.checkHintTextAndWarnings("amend", true, "trading names")
      registration.answerRadioButton("no")

      When("the user clicks change on the contact details")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink("contact-details\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("contact-details?waypoints=change-your-registration")

      Then("the correct warnings and hint text are displayed")
      crossSchema.checkHintTextAndWarnings("amend", true, "contact details")

      And("the intermediary changes their phone number")
      registration.updateField("telephoneNumber", "07111222111")
      registration.continue()
      email.completeEmailVerification("amend")

      When("the intermediary clicks change on bank details")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink("bank-account-details\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("bank-account-details?waypoints=change-your-registration")

      Then("the correct warnings and hint text are displayed")
      crossSchema.checkHintTextAndWarnings("amend", true, "bank details")

      And("the intermediary changes their bank account name")
      registration.updateField("accountName", "Cross Schema Name")
      registration.continue()

      And("the intermediary can submit their amended registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended")
      registration.checkAmendedAnswers("crossSchemaIoss")
      crossSchema.checkConfirmation(true)
    }

    Scenario(
      "Intermediary with a previous OSS registration has the correct text warnings in amend registration - does not make changes"
    ) {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("300000002", "Organisation", "crossSchemaAmendOss", "amend")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")

      When(
        "the intermediary selects the change link for Other trading names"
      )
      registration.selectChangeOrRemoveLink("add-other-trading-name\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=change-your-registration")

      And("the correct heading/warnings/hint text are on the page")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=change-your-registration")
      crossSchema.checkTradingNames("plural")
      crossSchema.checkHintTextAndWarnings("amend", true, "trading names")
      registration.answerRadioButton("no")

      When("the user clicks change on the contact details")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink("contact-details\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("contact-details?waypoints=change-your-registration")

      Then("the correct warnings and hint text are displayed")
      crossSchema.checkHintTextAndWarnings("amend", true, "contact details")
      registration.continue()
      email.completeEmailVerification("amend")

      When("the intermediary clicks change on bank details")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink("bank-account-details\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("bank-account-details?waypoints=change-your-registration")

      Then("the correct warnings and hint text are displayed")
      crossSchema.checkHintTextAndWarnings("amend", true, "bank details")
      registration.continue()

      And("the intermediary can submit their registration without any changes")
      registration.checkJourneyUrl("change-your-registration")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("no amendments and warnings are displayed on the confirmation")
      registration.checkAmendedAnswers("noAmendedAnswers")
      crossSchema.checkConfirmation(false)
    }

    Scenario("Intermediary with OSS registration has correct warnings/hint text when rejoining") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("300000002", "Organisation", "crossSchemaOssRejoin", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      // checks/amends

      Then("the intermediary can submit their registration and rejoin the service without changing any details")
      registration.submit()
      registration.checkJourneyUrl("successful-rejoin")
    }
  }
}
