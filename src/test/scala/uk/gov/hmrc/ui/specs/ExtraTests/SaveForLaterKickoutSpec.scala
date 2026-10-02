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

import uk.gov.hmrc.ui.pages.{Auth, Registration}
import uk.gov.hmrc.ui.specs.BaseSpec

class SaveForLaterKickoutSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth

  Feature("Save For Later kickout journeys") {

    Scenario("Intermediary returns to saved registration their UK VAT registration has now expired") {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("their VAT registration is now expired")
      auth.loginUsingAuthorityWizard("700000008", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the saved-progress-expired-vrn-date page")
      registration.checkJourneyUrl("saved-progress-expired-vrn-date")
    }

    Scenario(
      "Intermediary returns to saved registration and now has existing EU intermediary registration linked to UK VRN"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("their UK VRN is now registered for another intermediary registration")
      auth.loginUsingAuthorityWizard("333333111", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the saved-progress-client-already-registered page")
      registration.checkJourneyUrl("saved-progress-client-already-registered")
    }

    Scenario(
      "Intermediary returns to saved registration and now is quarantined on an existing EU intermediary registration linked to UK VRN"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("their UK VRN is now registered for another intermediary registration")
      auth.loginUsingAuthorityWizard("333333222", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the saved-progress-quarantined page")
      registration.checkJourneyUrl("saved-progress-quarantined")
    }

    Scenario(
      "Intermediary returns to saved registration where IOSS Number in previous registration data is active in another country"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("their previous registration data matches an active registration in another country")
      auth.loginUsingAuthorityWizard("100000111", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the saved-progress-client-already-registered page")
      registration.checkJourneyUrl("saved-progress-client-already-registered")
    }

    Scenario(
      "Intermediary returns to saved registration where IOSS Number in previous registration data is quarantined in another country"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("their previous registration data matches a quarantined registration in another country")
      auth.loginUsingAuthorityWizard("100000222", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the saved-progress-quarantined page")
      registration.checkJourneyUrl("saved-progress-quarantined")
    }

    Scenario(
      "Intermediary returns to saved registration where an EU VRN in the registration data is active in another country"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("their EU VRN matches an active registration in another country")
      auth.loginUsingAuthorityWizard("100000333", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the saved-progress-client-already-registered page")
      registration.checkJourneyUrl("saved-progress-client-already-registered")
    }

    Scenario(
      "Intermediary returns to saved registration where their EU Tax Reference in the registration data is quarantined in another country"
    ) {

      Given("the intermediary accesses the IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()

      When("their EU Tax Reference matches a quarantined registration in another country")
      auth.loginUsingAuthorityWizard("100000444", "Organisation", "vatOnly", "savedRegistration")

      Then("the intermediary is on the saved-progress-quarantined page")
      registration.checkJourneyUrl("saved-progress-quarantined")
    }
  }
}
