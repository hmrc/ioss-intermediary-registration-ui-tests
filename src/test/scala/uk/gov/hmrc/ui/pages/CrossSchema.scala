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

object CrossSchema extends BasePage {

  def checkTradingNames(version: String): Unit = {
    val header = Driver.instance.findElement(By.tagName("h1")).getText

    if (version == "singular") {
      Assert.assertTrue(header.equals("You have one UK trading name from a previous One Stop Shop scheme"))
    } else {
      Assert.assertTrue(header.equals("You have 2 UK trading names from a previous One Stop Shop scheme"))
    }
  }

  def checkHintTextAndWarnings(journey: String, displayed: Boolean, version: String): Unit = {
    val hintText    =
      "We have added the details you entered for a previous One Stop Shop scheme. Check they are still correct."
    val warningText =
      s"Changes you make here will also update the $version in any One Stop Shop or Import One Stop Shop accounts you registered for."

    val htmlBody = Driver.instance.findElement(By.tagName("body")).getText

    if (!displayed) {
      Assert.assertFalse(htmlBody.contains(warningText))
      if (journey == "registration") {
        Assert.assertFalse(htmlBody.contains(hintText))
      }
    } else {
      Assert.assertTrue(htmlBody.contains(warningText))
      if (journey == "registration") {
        Assert.assertTrue(htmlBody.contains(hintText))
      }
    }
  }

  def checkConfirmation(displayed: Boolean): Unit = {

    val htmlBody         = Driver.instance.findElement(By.tagName("body")).getText
    val confirmationText =
      "We've also updated any One Stop Shop or Import One Stop Shop registrations you have."

    if (!displayed) {
      Assert.assertFalse(htmlBody.contains(confirmationText))
    } else {
      Assert.assertTrue(htmlBody.contains(confirmationText))
    }
  }

}
