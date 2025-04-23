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
      auth.loginUsingAuthorityWizard("100000001")
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

      // currently redirects back to start of filter questions because the next section isn't developed yet

    }
  }
}
