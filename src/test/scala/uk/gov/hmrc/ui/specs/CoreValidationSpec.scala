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

class CoreValidationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Core Validation journeys") {

    Scenario("Intermediary with existing EU intermediary registration linked to UK VRN is not able to register") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("333333333", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.initialSteps()

      Then("the intermediary is on the scheme-still-active page")
      registration.checkJourneyUrl("scheme-still-active?countryCode=EE")
    }

    Scenario("Intermediary with existing EU intermediary quarantine linked to UK VRN is not able to register") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("333333334", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.initialSteps()

      Then("the intermediary is on the other-country-excluded-and-quarantined page")
      registration.checkJourneyUrl("other-country-excluded-and-quarantined?countryCode=EE&exclusionDate=")
    }

    Scenario("Intermediary with existing EU intermediary registration linked to EU Intermediary Number is not able to register - previous registrations") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")

      When("the intermediary enters a previous intermediary registration that is still active in another member state")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN7057777123")

      Then("the intermediary is on the scheme-still-active page")
      registration.checkJourneyUrl("scheme-still-active?countryCode=EE")
    }

    Scenario("Intermediary with quarantined EU intermediary registration linked to EU Intermediary Number is not able to register - previous registrations") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")

      When("the intermediary enters a previous intermediary registration that is quarantined in another member state")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/1")
      registration.selectCountry("Latvia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1")
      registration.enterAnswer("IN4287777123")

      Then("the intermediary is on the other-country-excluded-and-quarantined page")
      registration.checkJourneyUrl("other-country-excluded-and-quarantined?countryCode=EE&exclusionDate=")
    }

    Scenario("Intermediary with existing EU intermediary registration linked to EU Vat Number is not able to register - EU Vat Details") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")

      Then("the intermediary enters an EU registration linked to an Intermediary Number still active in another member state")
      registration.checkJourneyUrl("tax-in-eu")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Portugal")
      registration.checkJourneyUrl("eu-fixed-establishment/1")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("PT111222333")

      Then("the intermediary is on the scheme-still-active page")
      registration.checkJourneyUrl("scheme-still-active?countryCode=EE")
    }

    Scenario("Intermediary with existing EU intermediary quarantine linked to EU Vat Number is not able to register - EU Vat Details") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")

      Then("the intermediary enters an EU registration linked to an Intermediary Number quarantined in another member state")
      registration.checkJourneyUrl("tax-in-eu")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Lithuania")
      registration.checkJourneyUrl("eu-fixed-establishment/1")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1")
      registration.enterAnswer("LT999888777")

      Then("the intermediary is on the other-country-excluded-and-quarantined page")
      registration.checkJourneyUrl("other-country-excluded-and-quarantined?countryCode=EE&exclusionDate=")
    }

    Scenario("Intermediary with existing EU intermediary registration linked to EU Tax ID is not able to register - EU Vat Details") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")

      Then("the intermediary enters an EU registration linked to an Intermediary Number still active in another member state")
      registration.checkJourneyUrl("tax-in-eu")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Portugal")
      registration.checkJourneyUrl("eu-fixed-establishment/1")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/1")
      registration.enterAnswer("123LIS123")

      Then("the intermediary is on the scheme-still-active page")
      registration.checkJourneyUrl("scheme-still-active?countryCode=EE")
    }

    Scenario("Intermediary with existing EU intermediary quarantine linked to EU Tax ID is not able to register - EU Vat Details") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "vatOnly")
      registration.checkJourneyUrl("ioss-intermediary-registered")
      registration.initialSteps()
      registration.checkJourneyUrl("confirm-vat-details")
      registration.answerVatDetailsChoice("Yes")
      registration.checkJourneyUrl("have-uk-trading-name")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary")
      registration.answerRadioButton("no")

      Then("the intermediary enters an EU registration linked to an Intermediary Number quarantined in another member state")
      registration.checkJourneyUrl("tax-in-eu")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("eu-tax/1")
      registration.selectCountry("Lithuania")
      registration.checkJourneyUrl("eu-fixed-establishment/1")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("registration-tax-type/1")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/1")
      registration.enterAnswer("ABC123123")

      Then("the intermediary is on the other-country-excluded-and-quarantined page")
      registration.checkJourneyUrl("other-country-excluded-and-quarantined?countryCode=EE&exclusionDate=")
    }
  }
}
