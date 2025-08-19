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

object Auth extends BasePage {

  private val authUrl: String         = TestEnvironment.url("auth-login-stub") + "/auth-login-stub/gg-sign-in"
  private val registrationUrl: String =
    TestEnvironment.url("ioss-intermediary-registration-frontend")
  private val journeyUrl: String      = "/pay-clients-vat-on-eu-sales/register-import-one-stop-shop-intermediary"

  def goToAuthorityWizard(): Unit =
    get(authUrl)

  def checkAuthUrl(): Unit =
    getCurrentUrl should startWith(authUrl)

  def loginUsingAuthorityWizard(vrn: String, affinityGroup: String, enrolmentType: String, journey: String): Unit = {

    getCurrentUrl should startWith(authUrl)

    if (journey == "amend") {
      sendKeys(By.name("redirectionUrl"), s"$registrationUrl$journeyUrl/start-amend-journey")
    } else if (journey == "savedRegistration") {
      sendKeys(By.name("redirectionUrl"), s"$registrationUrl$journeyUrl/continue-on-sign-in")
    } else {
      sendKeys(By.name("redirectionUrl"), s"$registrationUrl$journeyUrl")
    }

    if (affinityGroup == "Agent") {
      selectByValue(By.id("affinityGroupSelect"), "Agent")
    } else if (affinityGroup == "Individual") {
      selectByValue(By.id("affinityGroupSelect"), "Individual")
      selectByValue(By.id("confidenceLevel"), "250")
      sendKeys(By.id("nino"), "AA123456D")
    } else {
      selectByValue(By.id("affinityGroupSelect"), "Organisation")
    }

    if (vrn != "None") {
      sendKeys(By.id("enrolment[0].name"), "HMRC-MTD-VAT")
      sendKeys(By.id("input-0-0-name"), "VRN")
      sendKeys(By.id("input-0-0-value"), vrn)
    }

    if (enrolmentType == "vatAndIossInt") {
      sendKeys(By.id("enrolment[1].name"), "HMRC-IOSS-INT")
      sendKeys(By.id("input-1-0-name"), "IntNumber")
      sendKeys(By.id("input-1-0-value"), "IN9001234567")
    }

    click(By.cssSelector("Input[value='Submit']"))

  }

}
