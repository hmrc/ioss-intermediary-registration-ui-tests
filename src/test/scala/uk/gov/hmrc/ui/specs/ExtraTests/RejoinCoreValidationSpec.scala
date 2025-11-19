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

package uk.gov.hmrc.ui.specs.ExtraTests

import uk.gov.hmrc.ui.pages.{Auth, Registration}
import uk.gov.hmrc.ui.specs.BaseSpec

class RejoinCoreValidationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Core Validation within Rejoin Registration journeys") {

    Scenario(
      "Intermediary with existing EU intermediary registration linked to UK VRN is not able to access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active scheme linked to their VRN")
      auth.loginUsingAuthorityWizard("333333333", "Organisation", "excludedPast", "rejoin")

      Then("the intermediary is on the scheme-still-active page")
      registration.checkJourneyUrl("scheme-still-active?countryCode=EE")
    }

    Scenario(
      "Intermediary with quarantined EU intermediary registration linked to UK VRN is not able to access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active quarantine linked to their VRN")
      auth.loginUsingAuthorityWizard("333333334", "Organisation", "excludedPast", "rejoin")

      Then("the intermediary is on the other-country-excluded-and-quarantined page")
      registration.checkJourneyUrl("other-country-excluded-and-quarantined?countryCode=EE&exclusionDate=")
    }

    Scenario(
      "Intermediary with existing EU intermediary registration linked to VRN in Fixed Establishments in the ETMP registration cannot access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active scheme linked to their VRN")
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "fixedEstablishmentActiveVrn", "rejoin")

      Then("the intermediary is on the cannot-rejoin-vat-number-already-registered/EE page")
      registration.checkJourneyUrl("cannot-rejoin-vat-number-already-registered/EE")
    }

    Scenario(
      "Intermediary with quarantined EU intermediary registration linked to VRN in Fixed Establishments in the ETMP registration cannot access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active scheme linked to their VRN")
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "fixedEstablishmentQuarantineVrn", "rejoin")

      Then("the intermediary is on the cannot-rejoin-vat-number-quarantined page")
      registration.checkJourneyUrl("cannot-rejoin-vat-number-quarantined")
    }

    Scenario(
      "Intermediary with existing EU intermediary registration linked to Tax Reference in Fixed Establishments in the ETMP registration cannot access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active scheme linked to their VRN")
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "fixedEstablishmentActiveTaxRef", "rejoin")

      Then("the intermediary is on the cannot-rejoin-vat-number-already-registered/EE page")
      registration.checkJourneyUrl("cannot-rejoin-vat-number-already-registered/EE")
    }

    Scenario(
      "Intermediary with quarantined EU intermediary registration linked to Tax Reference in Fixed Establishments in the ETMP registration cannot access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active scheme linked to their VRN")
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "fixedEstablishmentQuarantineTaxRef", "rejoin")

      Then("the intermediary is on the cannot-rejoin-vat-number-quarantined page")
      registration.checkJourneyUrl("cannot-rejoin-vat-number-quarantined")
    }

    Scenario(
      "Intermediary with existing EU intermediary registration linked to a previous registration in the ETMP registration cannot access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active scheme linked to their VRN")
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "previousRegistrationActive", "rejoin")

      Then("the intermediary is on the cannot-rejoin-registered-on-other-service/SI page")
      registration.checkJourneyUrl("cannot-rejoin-registered-on-other-service/SI")
    }

    Scenario(
      "Intermediary with quarantined EU intermediary registration linked to a previous registration in the ETMP registration cannot access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active scheme linked to their VRN")
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "previousRegistrationQuarantine", "rejoin")

      Then("the intermediary is on the cannot-rejoin-quarantined-by-other-country/LV page")
      registration.checkJourneyUrl("cannot-rejoin-quarantined-by-other-country/LV")
    }

    Scenario(
      "Intermediary cannot add previous registration that is still active in another country during rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Other IOSS intermediary registrations")
      registration.selectChangeOrRemoveLink(
        "has-previously-registered-as-intermediary\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the has-previously-registered-as-intermediary")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      And("the intermediary adds a previous registration that is already active in another member state")
      registration.checkJourneyUrl("previous-eu-country/1?waypoints=rejoin-check-your-details")
      registration.selectCountry("Slovenia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1?waypoints=rejoin-check-your-details")
      registration.enterAnswer("IN7057777123")

      Then("the intermediary is on the scheme-still-active page")
      registration.checkJourneyUrl("scheme-still-active?countryCode=EE")
    }

    Scenario(
      "Intermediary cannot add previous registration that is quarantined in another country during rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Other IOSS intermediary registrations")
      registration.selectChangeOrRemoveLink(
        "has-previously-registered-as-intermediary\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the has-previously-registered-as-intermediary")
      registration.checkJourneyUrl("has-previously-registered-as-intermediary?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      And("the intermediary adds a previous registration that is already active in another member state")
      registration.checkJourneyUrl("previous-eu-country/1?waypoints=rejoin-check-your-details")
      registration.selectCountry("Latvia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/1?waypoints=rejoin-check-your-details")
      registration.enterAnswer("IN4287777123")

      Then("the intermediary is on the other-country-excluded-and-quarantined page")
      registration.checkJourneyUrl("other-country-excluded-and-quarantined?countryCode=EE&exclusionDate=")
    }

    Scenario(
      "Intermediary cannot add a fixed establishment by VRN that is still active in another country during rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      Then("the intermediary can add tax details for one EU country that is already active in another member state")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1?waypoints=rejoin-check-your-details")
      registration.selectCountry("Portugal")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1?waypoints=rejoin-check-your-details")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=rejoin-check-your-details")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1?waypoints=rejoin-check-your-details")
      registration.enterAnswer("PT111222333")

      Then("the intermediary is on the scheme-still-active page")
      registration.checkJourneyUrl("scheme-still-active?countryCode=EE")
    }

    Scenario(
      "Intermediary cannot add a fixed establishment by VRN that is quarantined in another country during rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      Then("the intermediary can add tax details for one EU country that is already active in another member state")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1?waypoints=rejoin-check-your-details")
      registration.selectCountry("Lithuania")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1?waypoints=rejoin-check-your-details")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=rejoin-check-your-details")
      registration.selectRegistrationType("vat number")
      registration.checkJourneyUrl("eu-vat-number/1?waypoints=rejoin-check-your-details")
      registration.enterAnswer("LT999888777")

      Then("the intermediary is on the other-country-excluded-and-quarantined page")
      registration.checkJourneyUrl("other-country-excluded-and-quarantined?countryCode=EE&exclusionDate=")
    }

    Scenario(
      "Intermediary cannot add a fixed establishment by Tax Reference that is still active in another country during rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      Then("the intermediary can add tax details for one EU country that is already active in another member state")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1?waypoints=rejoin-check-your-details")
      registration.selectCountry("Portugal")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1?waypoints=rejoin-check-your-details")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=rejoin-check-your-details")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/1?waypoints=rejoin-check-your-details")
      registration.enterAnswer("123LIS123")

      Then("the intermediary is on the scheme-still-active page")
      registration.checkJourneyUrl("scheme-still-active?countryCode=EE")
    }

    Scenario(
      "Intermediary cannot add a fixed establishment by Tax Reference that is quarantined in another country during rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=rejoin-check-your-details"
      )

      And("the intermediary selects yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=rejoin-check-your-details")
      registration.answerRadioButton("yes")

      Then("the intermediary can add tax details for one EU country that is already active in another member state")
      registration.checkJourneyUrl("eu-fixed-establishment-country/1?waypoints=rejoin-check-your-details")
      registration.selectCountry("Lithuania")
      registration.checkJourneyUrl("eu-fixed-establishment-address/1?waypoints=rejoin-check-your-details")
      registration.enterFETradingName("Company Name")
      registration.enterFixedEstablishmentAddress("1 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=rejoin-check-your-details")
      registration.selectRegistrationType("tax id number")
      registration.checkJourneyUrl("eu-tax-identification-number/1?waypoints=rejoin-check-your-details")
      registration.enterAnswer("ABC123123")

      Then("the intermediary is on the other-country-excluded-and-quarantined page")
      registration.checkJourneyUrl("other-country-excluded-and-quarantined?countryCode=EE&exclusionDate=")
    }
  }
}
