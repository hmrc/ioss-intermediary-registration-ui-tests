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
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait}
import org.openqa.selenium.{By, Keys}
import uk.gov.hmrc.configuration.TestEnvironment
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
          "your contact name or department\n" + //hidden text
          "Telephone number 028 123 4567 Change\n" +
          "your telephone number\n" + //hidden text
          "Email address rocky.balboa@chartoffwinkler.co.uk Change\n" +
          "your email address\n" + //hidden text
          "Name on the account Chartoff Winkler and Co. Change\n" +
          "the name on this bank account\n" + //hidden text
          "BIC or SWIFT code BARCGB22456 Change\n" +
          "your BIC or SWIFT code\n" + //hidden text
          "IBAN GB33BUKB202015555555555 Change\n" +
          "your IBAN\n" + //hidden text
          "Confirm these changes to save them to your account."
      )
    )
  }

}
