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

import uk.gov.hmrc.ui.pages.{Auth, Registration}

class AmendRegistrationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

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

//    Extra scenarios to add when view registration API is implemented
    // Scenario("Intermediary removes all answers for a section when amending their registration - changing answers from yes to no")
//        - add for EU tax details, trading names
    // Scenario("Intermediary can amend and remove individual details from their existing registration answers")
//          - add for EU tax details, trading names

    Scenario("Intermediary can cancel their amended registration") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatAndIossInt", "amend")
      registration.checkJourneyUrl("change-your-registration")

      When("the intermediary clicks cancel on the change-your-registration page")
      registration.clickLink("cancel")

      And("the intermediary selects yes on the cancel-amend-registration page")
      registration.answerRadioButton("yes")

      Then("the intermediary is redirected to the Intermediary dashboard service")
      registration.checkDashboardJourneyUrl("your-account")

    }
  }
}
