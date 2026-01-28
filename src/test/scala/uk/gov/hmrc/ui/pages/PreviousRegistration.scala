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

import org.junit.Assert
import org.openqa.selenium.By
import uk.gov.hmrc.selenium.webdriver.Driver

object PreviousRegistration extends BasePage {

  def checkIntermediaryNumber(intermediaryNumber: String): Unit = {
    val body = Driver.instance.findElement(By.tagName("body")).getText
    Assert.assertTrue(body.contains(s"IOSS intermediary number: $intermediaryNumber"))
  }

  def checkChangeLinks(): Unit = {
    val body = Driver.instance.findElement(By.tagName("body")).getText

    Assert.assertTrue(
      body.contains(
        "Import One Stop Shop details\n" +
          "Have other trading names No\n" +
          "Other IOSS intermediary registrations No\n" +
          "Fixed establishments in other countries No\n" +
          "Contact name Rocky Balboa Change\n" +
          "Contact name\n" + // hidden text
          "Telephone number 028 123 4567 Change\n" +
          "Telephone number\n" + // hidden text
          "Email address rocky.balboa@chartoffwinkler.co.uk Change\n" +
          "Email address\n" + // hidden text
          "Name on the account Chartoff Winkler and Co. Change\n" +
          "Name on the account\n" + // hidden text
          "BIC or SWIFT code BARCGB22456 Change\n" +
          "BIC or SWIFT code\n" + // hidden text
          "IBAN GB33BUKB202015555555555 Change\n" +
          "IBAN\n" + // hidden text
          "Confirm these changes to save them to your account."
      )
    )
  }

  def selectPreviousRegistration(intermediaryNumber: String): Unit = {
    intermediaryNumber match {
      case "IN9007230002" => click(By.id("value_0"))
      case "IN9008230002" => click(By.id("value_1"))
      case _              => throw new Exception("Option doesn't exist")
    }
    click(continueButton)
  }

  def checkAmendedAnswersMultipleRegistrations(journey: String): Unit = {
    val body = Driver.instance.findElement(By.tagName("body")).getText

    journey match {
      case "onePreviousRegistrationCurrent"                                          =>
        Assert.assertTrue(
          body.contains(
            "You changed the following details:\n" +
              "Trading names removed tradingName2\n" +
              "Fixed establishments in other countries No\n" +
              "Fixed establishments in other countries removed Germany\n" +
              "France"
          )
        )
      case "multiplePreviousRegistrationsCurrent"                                    =>
        Assert.assertTrue(
          body.contains(
            "You changed the following details:\n" +
              "Have other trading names No\n" +
              "Trading names removed tradingName1\n" +
              "tradingName2\n" +
              "Other IOSS intermediary registrations details added Croatia"
          )
        )
      case "onePreviousRegistrationPrevious" | "multiplePreviousRegistrationsOldest" =>
        Assert.assertTrue(
          body.contains(
            "You changed the following details:\n" +
              "Contact name Previous Registration Test Name\n" +
              "Email address amend-test@email.com\n" +
              "BIC or SWIFT code (if you have one) Removed\n" +
              "IBAN GB91BKEN10000041610008"
          )
        )
      case "multiplePreviousRegistrationsPrevious"                                   =>
        Assert.assertTrue(
          body.contains(
            "You changed the following details:\n" +
              "Telephone number 01234 564712\n" +
              "Name on the account Previous registration bank-account-name\n" +
              "BIC or SWIFT code (if you have one) CITIGB2L"
          )
        )
      case _                                                                         =>
        throw new Exception("This amend variation does not exist")
    }
  }

}
