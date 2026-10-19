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

  private val registration         = Registration
  private val auth                 = Auth
  private val email                = EmailVerification
  private val crossSchema = CrossSchema

  Feature("Cross schema journeys") {

    Scenario("Registration for trader with an OSS registration") {

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

      Then("they are shown their trading names from their OSS registration and advised any changes will be made to those as well")
      crossSchema.checkTradingNames("singular")
      crossSchema.checkHintTextAndWarnings("registration", true, "trading names")

      And("the intermediary removes a trading name")
      registration.selectChangeOrRemoveLink("remove-other-trading-name\\/2")
      registration.checkJourneyUrl("remove-other-trading-name/2")
      registration.answerRadioButton("yes")

      And("the intermediary adds a new trading name")
      registration.checkJourneyUrl("add-other-trading-name")
      crossSchema.checkTradingNames("plural")
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

      Then("the contact details are already populated from their OSS registration and the correct warnings and hint text are shown")
      crossSchema.checkHintTextAndWarnings("registration", true, "contact details")

      And("the intermediary continues without updating any of the contact details")
      registration.continue()
      email.completeEmailVerification("registration")

      When("the intermediary is on the bank-account-details page")
      registration.checkJourneyUrl("bank-account-details")

      Then("the bank details are already populated from their OSS registration and the correct warnings and hint text are shown")
      crossSchema.checkHintTextAndWarnings("registration", true, "bank details")

      And("the intermediary continues without updating any of the bank details")
      registration.continue()

      When("the intermediary is on the check-your-answers page")
      registration.checkJourneyUrl("check-your-answers")

      And("the intermediary submits their registration")
      registration.submit()

      Then("the intermediary is shown the acknowledgment page with the correct text regarding updating other registrations")
      registration.checkJourneyUrl("successful")
      crossSchema.checkConfirmation(true)
    }
  }
}
