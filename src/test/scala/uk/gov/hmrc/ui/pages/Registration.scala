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

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import org.scalatest.matchers.should.Matchers.*
import uk.gov.hmrc.configuration.TestEnvironment

object Registration extends BasePage {

  private val registrationUrl: String =
    TestEnvironment.url("ioss-intermediary-registration-frontend")
  private val journeyUrl: String      = "/intermediary-ioss"

  def goToRegistrationJourney(): Unit =
    get(registrationUrl + journeyUrl)

  def checkJourneyUrl(page: String): Unit =
    getCurrentUrl should startWith(s"$registrationUrl$journeyUrl/$page")

  def answerRadioButton(answer: String): Unit = {

    answer match {
      case "yes" => click(By.id("value"))
      case "no"  => click(By.id("value-no"))
      case _     => throw new Exception("Option doesn't exist")
    }
    click(continueButton)
  }

  def answerVatDetailsChoice(answer: String): Unit = {
    answer match {
      case "Yes"                                           => click(By.id("value_0"))
      case "Yes, but some of my VAT details are incorrect" => click(By.id("value_1"))
      case "No, I want to register a different business"   => click(By.id("value_2"))
      case _                                               => throw new Exception("Option doesn't exist")
    }
    click(continueButton)
  }

  def continue(): Unit =
    click(continueButton)

  def goToPage(page: String): Unit =
    get(s"$registrationUrl$journeyUrl/$page")

  def enterAnswer(answer: String): Unit =
    sendKeys(By.id("value"), answer)
    click(continueButton)

  def selectChangeOrRemoveLink(link: String): Unit =
    click(By.cssSelector(s"a[href*=$link]"))

  def initialSteps(): Unit = {
    answerRadioButton("no")
    checkJourneyUrl("registered-for-vat-in-uk")
    answerRadioButton("yes")
    checkJourneyUrl("ni-or-eu-based")
    answerRadioButton("yes")
    checkJourneyUrl("register-to-use-service")
    continue()
  }
}
