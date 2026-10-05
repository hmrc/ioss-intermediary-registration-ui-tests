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

package uk.gov.hmrc.ui.data

import java.time.LocalDate

object SavedAnswers {

  val yesterday = LocalDate.now().minusDays(1)

  val data: List[String] =
    List(
      s"""
         |{
         |  "_id": {
         |    "$$oid": "6a47a4495ffd2910ac37e26b"
         |  },
         |  "vrn": "700000003",
         |  "data": "ibMeBUF4fNb777kLd6BmoC/iQXbVwxtYPEGknwTa6wYETnLKswEnhwsFRmo3Gh6DcHZ2HzvAoQgd0vUz7GAadg2VBCdZ05RTpaCe+19O2CPV2y/pbKVFkOTWfnfrR26SRqquUIY+jvZy6OIMSf3SZpbSOsqAecaghvoTmfcBMrDPMIRPUecjsnkaY3ZtdwxBL2EYhLOVoJLCfsrLhp8kBnuUuwg0qxWDeapwq4FXBDSPUcwXP1I65ZbImW+qfnpIPVIH8gilSFb2T63geAza1vQ+Icz8hCMeCj8QFZet319ia311SdWPHZBV0gyHvoOu4fuS7Sx2mSxiNvwr65/tRFu4bhVs1PDo6KFoV7wjTigvVHpAvLWU2+M64VAMm3nDNIM31huY3HmjPjlEdakxZeIvqRNUDFkHFztewy2saGZralonlAc/lUnp+lczwDDBYw==",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T12:00:07.020Z"
         |  }
         |}
        |""".stripMargin
    )
}
