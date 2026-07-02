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

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import uk.gov.hmrc.configuration.TestEnvironment

object EmailVerification extends BasePage {

  def completeEmailVerification(journey: String): Unit = {

    val emailUrl        = TestEnvironment.url("email-verification")
    val registrationUrl = TestEnvironment.url("ioss-intermediary-registration-frontend")

    fluentWait.until(ExpectedConditions.urlContains(s"$emailUrl/email-verification/journey"))

    val journeyId   = getCurrentUrl.split("/")(5)
    val testOnlyUrl =
      s"$registrationUrl/pay-clients-vat-on-eu-sales/register-import-one-stop-shop-intermediary/test-only/get-passcodes"
    get(testOnlyUrl)
    fluentWait.until(ExpectedConditions.urlContains(testOnlyUrl))

    val passcode = if (journey == "secondPreviousRegistration") {
      getText(By.tagName("body")).split("iossint@iossint.hmrc.gov.uk,")(2).dropRight(42)
    } else {
      getText(By.tagName("body")).split(">")(3).dropRight(3)
    }

    val emailVerificationUrl =
      s"$emailUrl/email-verification/journey/$journeyId/passcode?continueUrl=$registrationUrl/pay-clients-vat-on-eu-sales/register-import-one-stop-shop-intermediary/bank-account-details&origin=IOSS-Intermediary"
    get(emailVerificationUrl)
    fluentWait.until(ExpectedConditions.urlContains(emailVerificationUrl))

    sendKeys(By.id("passcode"), passcode)
    click(By.className("govuk-button"))

    val page = journey match {
      case "amend"                                               =>
        "change-your-registration"
      case "rejoin"                                              =>
        "rejoin-check-your-details"
      case "previousRegistration" | "secondPreviousRegistration" =>
        "change-a-previous-registration"
      case _                                                     => "bank-account-details"
    }

    fluentWait.until(
      ExpectedConditions.urlToBe(
        s"$registrationUrl/pay-clients-vat-on-eu-sales/register-import-one-stop-shop-intermediary/$page"
      )
    )
  }
}
