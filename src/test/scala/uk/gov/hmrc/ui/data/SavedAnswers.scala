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
         |  "data": "/a29CL6jTJXKT+YQP693zdpeJ2o9C9j1MR6lj7904bNhuvDkun7+aJjKcGAlOwrdpKZZnvrXsf9hTzEB8TZBMAPqAxaG2XRFZnBDa2zHf2ac+3gbSBXskJMgLZ6ZVTXs99EkdSrJ6C+6N8bHqtoN2nmrAcFAAC3DkezARiOzyRau1914ChcfMTbQruR7HEDy2bOT24+NWB1Twut04sBs/Ceuvg0TP5z58cLFcFd+1q+8tSZAu2Nq921qTgXqViS0ZhkT4MkfHxExn+GuV4sWcahIoczmW+CKI1r/mVS5tNHdb2r/+8euet1Kfk6vbUtdav+gtQ3VRPLkhHSSIqfb0Efks7dktNFlgCu4ZILW5iob+3KJFatwJAIhJIVkNipfxQ4iKpCVYtzsHZsxrr5ea56ph4GGPGRKNKJ+CbXicP+ODrOu8JojSvcg1z8BFaw3a3Q=",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T12:00:07.020Z"
         |  }
         |}
        |""".stripMargin
    )
}
