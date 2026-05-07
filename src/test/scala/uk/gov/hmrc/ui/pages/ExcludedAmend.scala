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

package uk.gov.hmrc.ui.pages

import org.junit.Assert
import org.openqa.selenium.By
import uk.gov.hmrc.selenium.webdriver.Driver

object ExcludedAmend extends BasePage {

  def checkChangeLinksManualNi(): Unit = {
    val body = Driver.instance.findElement(By.tagName("body")).getText

    Assert.assertTrue(
      body.contains(
        "Import One Stop Shop details\n" +
          "Business address in Northern Ireland Other Address Line 1\n" +
          "Other Address Line 2\n" +
          "Other Town or City\n" +
          "Other Region or State\n" +
          "BT111AH Change\n" +
          "Business address in Northern Ireland\n" + // hidden text
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

  def checkChangeLinksVatInfoNi(): Unit = {
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

  def checkAmendedAnswersExcludedIntermediary(journey: String): Unit = {
    val body = Driver.instance.findElement(By.tagName("body")).getText

    journey match {
      case "manualNiAddress" =>
        Assert.assertTrue(body.contains("")) // TODO add text
      case "postcode"        =>
        Assert.assertTrue(body.contains("")) // TODO add text
      case _                 =>
        throw new Exception("This amend variation does not exist")
    }
  }
}
