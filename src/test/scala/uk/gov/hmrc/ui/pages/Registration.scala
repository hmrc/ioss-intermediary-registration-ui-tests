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

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.{By, Keys}
import uk.gov.hmrc.configuration.TestEnvironment
import uk.gov.hmrc.selenium.webdriver.Driver
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait}
import org.junit.Assert

object Registration extends BasePage {

  private val registrationUrl: String =
    TestEnvironment.url("ioss-intermediary-registration-frontend")
  private val journeyUrl: String      = "/pay-clients-vat-on-eu-sales/register-import-one-stop-shop-intermediary"

  private val dashboardUrl: String        =
    TestEnvironment.url("ioss-intermediary-dashboard-frontend")
  private val dashboardJourneyUrl: String = "/pay-clients-vat-on-eu-sales/manage-ioss-returns-payments-clients"

  private val email = EmailVerification

  def goToRegistrationJourney(): Unit =
    get(registrationUrl + journeyUrl)

  def checkJourneyUrl(page: String): Unit =
    val url = s"$registrationUrl$journeyUrl/$page"
    fluentWait.until(ExpectedConditions.urlContains(url))
    getCurrentUrl.startsWith(url)

  def checkDashboardJourneyUrl(page: String): Unit =
    getCurrentUrl.startsWith(s"$dashboardUrl$dashboardJourneyUrl/$page")

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

  def submit(): Unit =
    click(submitButton)

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

  def fillBankAccountDetails(accountName: String, bicNumber: String, ibanNumber: String): Unit = {
    sendKeys(By.id("accountName"), accountName)
    sendKeys(By.id("bic"), bicNumber)
    sendKeys(By.id("iban"), ibanNumber)
    click(continueButton)
  }

  def checkVatDetailsPage(): Unit = {
    val h1 = Driver.instance.findElement(By.tagName("h1")).getText
    Assert.assertTrue(h1.equals("Confirm VAT details for your business"))
  }

  def checkIndividualName(): Unit = {
    val body = Driver.instance.findElement(By.tagName("body")).getText
    Assert.assertFalse(body.contains("Business name"))
    Assert.assertTrue(body.contains("Name first middle last"))
  }

  def updateField(id: String, text: String): Unit =
    sendKeys(By.id(id), text)

  def checkPrincipalPlaceOfBusiness(shouldBeDisplayed: Boolean): Unit = {
    val body        = Driver.instance.findElement(By.tagName("body")).getText
    val textToCheck = "Principal place of business address"
    if (shouldBeDisplayed) {
      Assert.assertTrue(body.contains(textToCheck))
    } else {
      Assert.assertFalse(body.contains(textToCheck))
    }
  }

  def checkNiAddressOnCya(shouldBeDisplayed: Boolean): Unit = {
    val body        = Driver.instance.findElement(By.tagName("body")).getText
    val textToCheck = "Business address in Northern Ireland"
    if (shouldBeDisplayed) {
      Assert.assertTrue(body.contains(textToCheck))
    } else {
      Assert.assertFalse(body.contains(textToCheck))
    }
  }

  def enterNiAddress(
    line1: String,
    line2: String,
    townOrCity: String,
    county: String,
    postCode: String
  ): Unit =
    sendKeys(By.id("line1"), line1)
    sendKeys(By.id("line2"), line2)
    sendKeys(By.id("townOrCity"), townOrCity)
    sendKeys(By.id("county"), county)
    sendKeys(By.id("postCode"), postCode)
    click(continueButton)

  def enterFETradingName(tradingName: String): Unit =
    sendKeys(By.id("tradingName"), tradingName)

  def clearCountry(): Unit = {
    val input = Driver.instance.findElement(By.id("value")).getAttribute("value")
    if (input != null) {
      for (n <- input)
        Driver.instance.findElement(By.id("value")).sendKeys(Keys.BACK_SPACE)
    }
  }

  def submitMinimalRegistration(): Unit = {
    answerRadioButton("no")
    checkJourneyUrl("registered-for-vat-in-uk")
    answerRadioButton("yes")
    checkJourneyUrl("ni-or-eu-based")
    answerRadioButton("yes")
    checkJourneyUrl("register-to-use-service")
    continue()
    checkJourneyUrl("confirm-vat-details")
    answerVatDetailsChoice("Yes")
    checkJourneyUrl("have-other-trading-name")
    answerRadioButton("no")
    checkJourneyUrl("has-previously-registered-as-intermediary")
    answerRadioButton("no")
    checkJourneyUrl("eu-fixed-establishment")
    answerRadioButton("no")
    checkJourneyUrl("contact-details")
    fillContactDetails("Example Name", "24242424234", "test-name@email.co.uk")
    email.completeEmailVerification("registration")
    checkJourneyUrl("bank-account-details")
    fillBankAccountDetails("Accountname", "SMCOGB2LXXM", "GB29NWBK60161331926819")
    checkJourneyUrl("check-your-answers")
    submit()
  }

  def clickLink(link: String): Unit =
    click(By.id(link))

  def saveAndComeBackLater(): Unit =
    click(By.id("saveProgress"))

  def checkProblemPage(): Unit = {
    val h1 = Driver.instance.findElement(By.tagName("h1")).getText
    Assert.assertTrue(h1.equals("Sorry, there is a problem with the service"))
  }

  def checkChangeRemoveLinks(): Unit = {
    val body = Driver.instance.findElement(By.tagName("body")).getText
    Assert.assertFalse(body.contains("Change"))
    Assert.assertFalse(body.contains("Remove"))
  }

  def checkAmendedAnswers(amendJourney: String): Unit = {
    val body = Driver.instance.findElement(By.tagName("body")).getText

    amendJourney match {
      case "noAmendedAnswers"              =>
        Assert.assertTrue(body.contains("You have not changed any of your registration details."))
      case "noToYes"                       =>
        Assert.assertTrue(body.contains("You changed the following details:"))
        Assert.assertTrue(body.contains("Have a different UK trading name Yes"))
        Assert.assertTrue(body.contains("Trading names added first amend trading name"))
        Assert.assertTrue(body.contains("amend trading 2!"))
        Assert.assertTrue(body.contains("Other IOSS intermediary registrations Yes"))
        Assert.assertTrue(body.contains("Other IOSS intermediary registrations details added Slovenia"))
        Assert.assertTrue(body.contains("Netherlands"))
        Assert.assertTrue(body.contains("Fixed establishments in other countries Yes"))
        Assert.assertTrue(body.contains("Fixed establishments in other countries added Denmark"))
        Assert.assertTrue(body.contains("Slovakia"))
      case "contactAndBankDetails"         =>
        Assert.assertTrue(body.contains("You changed the following details:"))
        Assert.assertTrue(body.contains("Contact name Amended Test Name"))
        Assert.assertTrue(body.contains("Email address amend-test@email.com"))
        Assert.assertTrue(body.contains("BIC or SWIFT code (if you have one) Removed"))
        Assert.assertTrue(body.contains("IBAN GB91BKEN10000041610008"))
      case "removeAll"                     =>
        Assert.assertTrue(body.contains("You changed the following details:"))
        Assert.assertTrue(body.contains("Have a different UK trading name No"))
        Assert.assertTrue(body.contains("Trading names removed tradingName1"))
        Assert.assertTrue(body.contains("tradingName2"))
        Assert.assertTrue(body.contains("Fixed establishments in other countries No"))
        Assert.assertTrue(body.contains("Fixed establishments in other countries removed Germany"))
        Assert.assertTrue(body.contains("France"))
      case "tradingAndFixedEstablishments" =>
        Assert.assertTrue(body.contains("You changed the following details:"))
        Assert.assertTrue(body.contains("Trading names added an amended trading name"))
        Assert.assertTrue(body.contains("Trading names removed tradingName1"))
        Assert.assertTrue(body.contains("tradingName2"))
        Assert.assertTrue(body.contains("Fixed establishments in other countries added Malta"))
        Assert.assertTrue(body.contains("Fixed establishments in other countries removed Germany"))
        Assert.assertTrue(body.contains("France"))
      case "previousRegistrations"         =>
        Assert.assertTrue(body.contains("You changed the following details:"))
        Assert.assertTrue(body.contains("Other IOSS intermediary registrations details added Croatia"))
      case "niAddress"                     =>
        Assert.assertTrue(body.contains("You changed the following details:"))
        Assert.assertTrue(body.contains("Business address in Northern Ireland changed 1A Different Road"))
        Assert.assertTrue(body.contains("Suburb"))
        Assert.assertTrue(body.contains("Belfast"))
        Assert.assertTrue(body.contains("BT1 1DD"))

      case _ =>
        throw new Exception("This amend variation does not exist")
    }
  }
}
