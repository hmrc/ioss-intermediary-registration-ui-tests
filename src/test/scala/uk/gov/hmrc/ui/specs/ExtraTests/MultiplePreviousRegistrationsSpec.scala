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

import uk.gov.hmrc.ui.pages.{Auth, EmailVerification, PreviousRegistration, Registration}
import uk.gov.hmrc.ui.specs.BaseSpec

class MultiplePreviousRegistrationsSpec extends BaseSpec {

  private val registration         = Registration
  private val auth                 = Auth
  private val email                = EmailVerification
  private val previousRegistration = PreviousRegistration

  Feature("Multiple previous registration amend journeys") {

    Scenario("Intermediary with a current registration and one previous registration") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "onePreviousRegistration", "amend")

      When("the intermediary's current registration is displayed")
      registration.checkJourneyUrl("change-your-registration")
      previousRegistration.checkIntermediaryNumber("IN9008230001")

      Then("the intermediary can select the 'View or change your previous registration' link")
      registration.clickLink("change-previous-registrations")

      And("the intermediary selects yes to view their one previous registration")
      registration.checkJourneyUrl("change-your-previous-registration?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary's previous registration is displayed")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9007230001")

      And("change links are only displayed for Contact Details and Bank Details")
      previousRegistration.checkChangeLinks()

      Then("the intermediary can amend contact details in their previous registration")
      registration.selectChangeOrRemoveLink("contact-details\\?waypoints\\=change-a-previous-registration")
      registration.checkJourneyUrl("contact-details?waypoints=change-a-previous-registration")
      registration.updateField("fullName", "Previous Registration Test Name")
      registration.updateField("emailAddress", "amend-test@email.com")
      registration.continue()
      email.completeEmailVerification("previousRegistration")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9007230001")

      And("the intermediary can amend bank details in their previous registration")
      registration.selectChangeOrRemoveLink("bank-account-details\\?waypoints\\=change-a-previous-registration")
      registration.checkJourneyUrl("bank-account-details?waypoints=change-a-previous-registration")
      registration.updateField("bic", "")
      registration.updateField("iban", "GB91BKEN10000041610008")
      registration.continue()

      And("the intermediary can successfully submit their amended previous registration")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9007230001")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended in their previous registration")
      previousRegistration.checkAmendedAnswersMultipleRegistrations("onePreviousRegistrationPrevious")

      When("the intermediary manually navigates back to their current registration")
      registration.goToPage("start-amend-journey")
      registration.checkJourneyUrl("change-your-registration")
      previousRegistration.checkIntermediaryNumber("IN9008230001")

      Then("the intermediary clicks change for Other trading names")
      registration.selectChangeOrRemoveLink("add-other-trading-name\\?waypoints\\=change-your-registration")

      And("the intermediary removes the second trading name")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink("remove-other-trading-name\\/2\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("remove-other-trading-name/2?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("add-other-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary changes Fixed establishments in other countries to No")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink("eu-fixed-establishment\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("remove-all-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      Then("the intermediary can submit their amended registration successfully")
      registration.checkJourneyUrl("change-your-registration")
      previousRegistration.checkIntermediaryNumber("IN9008230001")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended in their current registration")
      previousRegistration.checkAmendedAnswersMultipleRegistrations("onePreviousRegistrationCurrent")
    }

    Scenario("Intermediary with a current registration and two previous registrations") {

      Given("the intermediary accesses the amend journey within IOSS Intermediary Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard("100000001", "Organisation", "multiplePreviousRegistrations", "amend")

      When("the intermediary's current registration is displayed")
      registration.checkJourneyUrl("change-your-registration")
      previousRegistration.checkIntermediaryNumber("IN9009230002")

      Then("the intermediary can select the 'View or change a previous registration' link")
      registration.clickLink("change-previous-registrations")

      And("the intermediary selects previous registration IN9007230002")
      registration.checkJourneyUrl("change-your-previous-registrations?waypoints=change-your-registration")
      previousRegistration.selectPreviousRegistration("IN9007230002")

      And("the correct previous registration is displayed")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9007230002")

      And("change links are only displayed for Contact Details and Bank Details")
      previousRegistration.checkChangeLinks()

      Then("the intermediary can amend contact details in their previous registration")
      registration.selectChangeOrRemoveLink("contact-details\\?waypoints\\=change-a-previous-registration")
      registration.checkJourneyUrl("contact-details?waypoints=change-a-previous-registration")
      registration.updateField("fullName", "Previous Registration Test Name")
      registration.updateField("emailAddress", "amend-test@email.com")
      registration.continue()
      email.completeEmailVerification("previousRegistration")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9007230002")

      And("the intermediary can amend bank details in their previous registration")
      registration.selectChangeOrRemoveLink("bank-account-details\\?waypoints\\=change-a-previous-registration")
      registration.checkJourneyUrl("bank-account-details?waypoints=change-a-previous-registration")
      registration.updateField("bic", "")
      registration.updateField("iban", "GB91BKEN10000041610008")
      registration.continue()

      And("the intermediary can successfully submit their amended previous registration")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9007230002")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended in their previous registration")
      previousRegistration.checkAmendedAnswersMultipleRegistrations("multiplePreviousRegistrationsOldest")

      When("the intermediary manually navigates back to their current registration")
      registration.goToPage("start-amend-journey")
      registration.checkJourneyUrl("change-your-registration")
      previousRegistration.checkIntermediaryNumber("IN9009230002")

      Then("the intermediary can select the 'View or change a previous registration' link")
      registration.clickLink("change-previous-registrations")

      And("the intermediary selects previous registration IN9008230002")
      registration.checkJourneyUrl("change-your-previous-registrations?waypoints=change-your-registration")
      previousRegistration.selectPreviousRegistration("IN9008230002")

      And("the correct previous registration is displayed")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9008230002")

      And("change links are only displayed for Contact Details and Bank Details")
      previousRegistration.checkChangeLinks()

      Then("the intermediary can amend contact details in their previous registration")
      registration.selectChangeOrRemoveLink("contact-details\\?waypoints\\=change-a-previous-registration")
      registration.checkJourneyUrl("contact-details?waypoints=change-a-previous-registration")
      registration.updateField("telephoneNumber", "01234 564712")
      registration.continue()
      email.completeEmailVerification("secondPreviousRegistration")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9008230002")

      And("the intermediary can amend bank details in their previous registration")
      registration.selectChangeOrRemoveLink("bank-account-details\\?waypoints\\=change-a-previous-registration")
      registration.checkJourneyUrl("bank-account-details?waypoints=change-a-previous-registration")
      registration.updateField("accountName", "Previous registration bank-account-name")
      registration.updateField("bic", "CITIGB2L")
      registration.continue()

      And("the intermediary can successfully submit their amended previous registration")
      registration.checkJourneyUrl("change-a-previous-registration")
      previousRegistration.checkIntermediaryNumber("IN9008230002")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended in their previous registration")
      previousRegistration.checkAmendedAnswersMultipleRegistrations("multiplePreviousRegistrationsPrevious")

      When("the intermediary manually navigates back to their current registration")
      registration.goToPage("start-amend-journey")
      registration.checkJourneyUrl("change-your-registration")
      previousRegistration.checkIntermediaryNumber("IN9009230002")

      And("the intermediary changes Have other trading names to No")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink("have-other-trading-name\\?waypoints\\=change-your-registration")
      registration.checkJourneyUrl("have-other-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("remove-all-trading-names?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary adds another IOSS intermediary registration")
      registration.checkJourneyUrl("change-your-registration")
      registration.selectChangeOrRemoveLink(
        "add-previous-intermediary-registration\\?waypoints\\=change-your-registration"
      )
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("previous-eu-country/2?waypoints=change-your-registration")
      registration.selectCountry("Croatia")
      registration.checkJourneyUrl("previous-intermediary-registration-number/2?waypoints=change-your-registration")
      registration.enterAnswer("IN1917777777")
      registration.checkJourneyUrl("add-previous-intermediary-registration?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      Then("the intermediary can submit their amended registration successfully")
      registration.checkJourneyUrl("change-your-registration")
      previousRegistration.checkIntermediaryNumber("IN9009230002")
      registration.submit()
      registration.checkJourneyUrl("successful-amend")

      And("the correct details are shown as amended in their current registration")
      previousRegistration.checkAmendedAnswersMultipleRegistrations("multiplePreviousRegistrationsCurrent")
    }
  }
}
