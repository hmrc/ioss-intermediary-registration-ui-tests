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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.{Auth, EmailVerification, Registration}

class RejoinRegistrationSpec extends BaseSpec {

  private val registration = Registration
  private val auth         = Auth
  private val email        = EmailVerification

  Feature("Rejoin registration journeys") {

    Scenario("Intermediary with an exclusion effective date in the past but not quarantined can rejoin the service") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedPast", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")
    }

    Scenario("Intermediary with an expired quarantine can rejoin the service") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "quarantineExpired", "rejoin")
      registration.checkJourneyUrl("rejoin-check-your-details")
    }

    Scenario("Intermediary with an exclusion effective date in the future cannot access the rejoin journey") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "excludedFuture", "rejoin")
      registration.checkJourneyUrl("")
    }

    Scenario("Intermediary with an active quarantine cannot access the rejoin journey") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "quarantined", "rejoin")
      registration.checkJourneyUrl("")
    }

    Scenario("Intermediary who reversed their exclusion cannot access the rejoin journey") {

      Given("the intermediary accesses the rejoin journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "reversal", "rejoin")
      registration.checkJourneyUrl("")
    }




  }
}
