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
import uk.gov.hmrc.selenium.webdriver.Driver

object EmailVerification extends BasePage {

  def completeEmailVerification(): Unit = {
    val journeyId = getCurrentUrl.split("/")(5)
    get(
      "http://localhost:10184/intermediary-ioss/test-only/get-passcodes"
    )

    val passcode = Driver.instance.findElement(By.tagName("body")).getText.split(">")(3).dropRight(3)

    get(
      s"http://localhost:9890/email-verification/journey/$journeyId/passcode?continueUrl=http://localhost:10184/intermediary-ioss/bank-account-details&origin=IOSS-Intermediary"
    )
    Driver.instance.findElement(By.id("passcode")).sendKeys(passcode)
    Driver.instance.findElement(By.className("govuk-button")).click()
  }

}
