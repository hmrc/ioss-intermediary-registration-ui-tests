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
import uk.gov.hmrc.selenium.webdriver.Driver
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait}

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

  def waitForElement(by: By): Unit =
    new FluentWait(Driver.instance).until(ExpectedConditions.presenceOfElementLocated(by))

  def selectCountry(country: String): Unit = {
    val inputId = "value"
    sendKeys(By.id(inputId), country)
    waitForElement(By.id(inputId))
    click(By.cssSelector("li#value__option--0"))
    click(continueButton)
  }

  def selectRegistrationType(data: String): Unit = {
    data match {
      case "vat number"    => click(By.id("value_0"))
      case "tax id number" => click(By.id("value_1"))
      case _               => throw new Exception("Option doesn't exist")
    }
    continue()
  }

  def enterFixedEstablishmentAddress(
    line1: String,
    line2: String,
    townOrCity: String,
    stateOrRegion: String,
    postCode: String
  ): Unit =
    sendKeys(By.id("line1"), line1)
    sendKeys(By.id("line2"), line2)
    sendKeys(By.id("townOrCity"), townOrCity)
    sendKeys(By.id("stateOrRegion"), stateOrRegion)
    sendKeys(By.id("postCode"), postCode)
    click(continueButton)

  def fillContactDetails(name: String, phone: String, email: String): Unit = {
    sendKeys(By.id("fullName"), name)
    sendKeys(By.id("telephoneNumber"), phone)
    sendKeys(By.id("emailAddress"), email)
    click(continueButton)

  }
}
