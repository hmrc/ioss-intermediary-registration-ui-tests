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

class ChangeAnswersSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Registration journeys") {

    Scenario("Intermediary changes and removes answers whilst using the IOSS Intermediary Registration Service") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation")
      registration.checkJourneyUrl("ioss-intermediary-registered")

      When("the intermediary answers the initial filter questions")
      registration.initialSteps()

      Then("the intermediary selects yes on the confirm-vat-details page")
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")

      And("the intermediary selects yes on the have-uk-trading-name page")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("yes")

      And("the intermediary adds the first trading name")
      registration.checkJourneyUrl("uk-trading-name/1")
      registration.enterAnswer("1st trading name")

      And("the intermediary selects yes on the add-uk-trading-name page")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.answerRadioButton("yes")

      And("the intermediary adds the second trading name")
      registration.checkJourneyUrl("uk-trading-name/2")
      registration.enterAnswer("2nd-trading-name")

      And("the intermediary clicks change on the add-uk-trading-name page for first trading name")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.selectChangeOrRemoveLink("uk-trading-name\\/1\\?waypoints\\=change-add-uk-trading-name")

      And("the intermediary amends the first trading name")
      registration.checkJourneyUrl("uk-trading-name/1?waypoints=change-add-uk-trading-name")
      registration.enterAnswer("a different first trading name")

      And("the intermediary clicks remove on the add-uk-trading-name page for second trading name")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.selectChangeOrRemoveLink("remove-uk-trading-name\\/2")

      And("the intermediary selects yes on the remove-uk-trading-name page")
      registration.checkJourneyUrl("remove-uk-trading-name/2")
      registration.answerRadioButton("yes")

      And("the intermediary selects no on the add-uk-trading-name page")
      registration.checkJourneyUrl("add-uk-trading-name")
      registration.answerRadioButton("no")

      // currently redirects to Check Your Answers page as full journey isn't developed yet

      // adding manual browsing to EU details section as previous registration section is not developed yet
      Then("the intermediary selects yes on the tax-in-eu page")
      registration.goToPage("tax-in-eu")
      registration.checkJourneyUrl("tax-in-eu")
      registration.answerRadioButton("yes")

      And("the intermediary selects Croatia on the first eu-tax page")
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Croatia")

      And("the intermediary selects yes on the first eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment/1")
      registration.answerRadioButton("yes")

      And("the intermediary selects VAT number on the first registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")

      And("the intermediary adds the VAT registration number for Croatia")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("HR11114567888")

      And("the intermediary adds the trading name for Croatia")
      registration.checkJourneyUrl("eu-trading-name/1")
      registration.enterAnswer("Croatia Trading")

      And("the intermediary adds the fixed establishment address for Croatia")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Split", "", "HR 65487")

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
      registration.selectChangeOrRemoveLink("eu-trading-name\\/1\\?waypoints\\=check-tax-details-1")
      registration.checkJourneyUrl("eu-trading-name/1")
      registration.enterAnswer("Croatia Trading New")

      And("the intermediary selects continue on the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()

      And("the intermediary selects yes on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("yes")

      And("the intermediary selects Slovenia on the second eu-tax page")
      registration.checkJourneyUrl("eu-tax/2")
      registration.selectCountry("Slovenia")

      And("the intermediary selects yes on the second eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment/2")
      registration.answerRadioButton("yes")

      And("the intermediary selects tax id number on the second registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/2")
      registration.selectRegistrationType("tax id number")

      And("the intermediary adds the tax identification number for Slovenia")
      registration.checkJourneyUrl("eu-tax-identification-number/2")
      registration.enterAnswer("SLOV 123 456")

      And("the intermediary adds the trading name for Slovenia")
      registration.checkJourneyUrl("eu-trading-name/2")
      registration.enterAnswer("Slovenia Goods 55")

      And("the intermediary adds the fixed establishment address for Slovenia")
      registration.checkJourneyUrl("eu-fixed-establishment-address/2")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Koper", "", "")

      And("the intermediary selects continue on the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details")
      registration.continue()

      And("the intermediary clicks remove on the add-tax-details page for second EU registration")
      registration.checkJourneyUrl("add-tax-details")
      registration.selectChangeOrRemoveLink("remove-tax-details\\/2")

      And("the intermediary selects yes on the remove-tax-details page")
      registration.checkJourneyUrl("remove-tax-details/2")
      registration.answerRadioButton("yes")

      And("the intermediary selects no on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details")
      registration.answerRadioButton("no")

      // missing steps in journey to be completed later
      // adding manual browsing to check-your-answers-page
      When("the intermediary submits the registration on the check-your-answers page")
      registration.goToPage("check-your-answers")
      registration.submit()

      Then("the intermediary is on the successful submission page")
      registration.goToPage("successful")

    }
  }
}
