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

      Then("the intermediary is on the <page> page")
      registration.checkJourneyUrl("")
    }

    Scenario(
      "Intermediary with quarantined EU intermediary registration linked to UK VRN is not able to access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active quarantine linked to their VRN")
      auth.loginUsingAuthorityWizard("333333334", "Organisation", "excludedPast", "rejoin")

      Then("the intermediary is on the <page> page")
      registration.checkJourneyUrl("")
    }

    Scenario(
      "Intermediary with existing EU intermediary registration linked to VRN in Fixed Establishments in the ETMP registration cannot access the rejoin intermediary registration journey"
    ) {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("the intermediary has an active scheme linked to their VRN")
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "fixedEstablishmentActiveVrn", "rejoin")

      Then("the intermediary is on the cannot-rejoin-vat-number-already-registered/DE page")
      registration.checkJourneyUrl("cannot-rejoin-vat-number-already-registered/DE")
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

      Then("the intermediary is on the cannot-rejoin-vat-number-already-registered/DE page")
      registration.checkJourneyUrl("cannot-rejoin-vat-number-already-registered/DE")
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
  }
}
