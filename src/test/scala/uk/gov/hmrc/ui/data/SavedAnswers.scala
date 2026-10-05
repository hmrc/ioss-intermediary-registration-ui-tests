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
        |""".stripMargin,
      s"""
         |{
         |  "_id": {
         |    "$$oid": "6abe17078bae596cbbece568"
         |  },
         |  "vrn": "333333111",
         |  "data": "sqSdZqWCP0f+bJbHXArPpa9Pz27VQgeGshV/zTStqRuCSBaoeSDURgnwc43Ve5lQ4FzCzUwZvFi6kSokidoh8j5t6tKk91epsjFhdid2I5YlozFYkF82f7TR0Ubl9trK9dWbtZWy0ELRkfBSyhTfbv7aCI7Ufw3amNQqUngy16HwDLcNiH21X5PZNwVVTXd0RcKzANJmW+EbPvzNHIcm0pzKUQLNEP55kP3+yu1hBy3tk3DzTnN39Ku4RybP7NxB9sHjctEDmpO8CuIUNtPiqFugKXhb/aWlUzZ9iM3GPY5j4cl0Ea/cpSAR+2IDHiwva7xkHUtXyJFTyZQJB3pkuzSj2IzCGrPUqNZwZa5sxtPS3zSEYV/OAf+SDSJptt/jlC1IhWorysEjgSBSokiIEVMvl1UK5xZbEUX14ClnaRAPLQOy7NV0EbIJHL7JmSu/Aix27P4VAKwhOgbC7ulay1vFPnwASdozlXSWPEL/UWpdXq3ZfcwCbVsZzNGqWPr32D7dvSbHJFDAnc1MC+vDspP+1pX9B4s1BAiBZfCdWaoMX5jYJKNSCw==",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T08:17:11.490Z"
         |  }
         |}
         |""".stripMargin,
      s"""
         |{
         |  "_id": {
         |    "$$oid": "6abe1dfa8bae596cbbf3ffef"
         |  },
         |  "vrn": "700000008",
         |  "data": "rielx5eKNv6772Ja88QzunRK9WT/QfwZzmN+V3c7Vcg7jS9L3yTZ/xwAAOV57dxPGAeCmSJWdsRImqlZ7nhtc+8AkhWQyb06/wJ3XtHXpFnI2MTJovBQ5skWGkUrs0Y3ZPrKq0AZex7+AuIQhp4lIpfErl1weGf+MA9vrW9nplCNzJu3OsbaMD2w6+hgcgEjU7TwcIILrGS0O3CY02JxkKIQK/V+463+lq2iijwOeEShWv24jZjch3zpoh6XjpldlGcSIB/drVJ0iA7Z0NRs4RMr4W7U631XBNG1tyj5oUT/ueBiAzs2L/OthdcVqW7bfDb48AMLhasiwu6PpmrT0hwYuKBCMTmx4YU/kBtDAgYGUeJwmgpy/r5EtMi368O6TKKhMIKfewT/wY6HyGqDM6ouwY1/mtXGrL1A3Jhv8BKeLw44YIMhZ+049uu+BtTVAZ2QVjwQirsjhrrNmPsQ2J9GA6s411sHk0HE7JPDn+eJdur2azp3bZMJ6ne11t7qxPF1Sg==",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T08:46:50.223Z"
         |  }
         |}
         |""".stripMargin,
      s"""
         |{
         |  "_id": {
         |    "$$oid": "6abe51068bae596cbb0ca9f1"
         |  },
         |  "vrn": "333333222",
         |  "data": "RvhIQu7hRdSxzbuZuUxhXKwY9hxZdSY1f7xcytuUHbG4Q71dY/ZTP/KyJSVYSrSGIisbSx0ExLHu7SEyttHSI2LLQqzvTzNSVseKgLmVLuYLUv3fsjOWph3PtluV6bUgm2+7iL1HSt9OPoifdkM5FJDpZYwOrkqX7djx2AHRsbhIcuNET89XFjvakUtI9Joc3Jow0LACfOF3sNY8+lgbW55i9TDmKRD77iAjnTip0L1c7Kf7+vyGpOiJZD3jh8RYgOp/jEgSnqf8Z17KrGcQaOlqUW4ak09wKKtHUfSr/9TSz3FNxOw4xnagHan8C91+WOhcxj8jA3tI08igeEZFyVmaXbZz/8lMXc78fZcBEfZG1gmyaoMETTQrKJd3+JdXhg5GL0SiaBGztQfNVHEnVF9aIuHG0qHaDM/nRHXnc2Wxk/yq3PoMJSPzYWbBMC84zrs=",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T12:24:38.024Z"
         |  }
         |}
         |""".stripMargin,
      s"""
         |{
         |  "_id": {
         |    "$$oid": "6abe535a8bae596cbb0f0896"
         |  },
         |  "vrn": "100000111",
         |  "data": "dNlfZS3XlrybX8IJ5OEpuk/3TL5GAuxm5a/ROZpxGkgrQUNV/GGh5irsr0fEmbnOdSp5tcC5w19qA9gJJwYjaSWjRDePNFgi42ay8thvtZKbXZW/K6+xTaxnGXsIkrHiRxWWvmkZGDXcRXM6Y6Bq1F1WK1TWwQ7fPNLNDS3kZOdEQkbiCNkq4pkNXZy4jUvq3fsjOKx/k8xAambB7U6mu7Zpp34RvPnV1Eb17w9dyfLf8roL8UH5U3mIPokpzs12kGVfw+Hxugs5khnGhziEK6Z8DXxzrh8AxUUaS7Gi30m/i93wJ4mTKm32/9NiCfu/Sk1vBGflCBPSXrLhq3DWSWrPcGq4XwLK7BcPYvbSI6hquxZ0286uFU8mVIUZRx4bfHxb7i7CrzVdnWOyo7ehk1xOYyXO/C34hdz4btp10mfKimMPxHOsggM4BLZtfqCga46tZrPDMe7cqnEQHcM1KefwI22wjruoThEK5oZjJ33pcMjdFXbKORmwSdUbXCWtaJhLr/IySWU9XZde7lFJoL3K0ikcpFDm4hrItmvIyMIgoGaQF0reTrP2JY2JNk0TjqFoh5vTogELD9mDsVsUzBkQrc1VtICRBCXjhENhl2YI46WgRjF9VqeoVaO4jOs/5X+cL+V+r5TapaSTuL+MqWvPi+g8LFWv3pyylIpDzRcl877m",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T12:34:34.589Z"
         |  }
         |}
         |""".stripMargin,
      s"""
         |{
         |  "_id": {
         |    "$$oid": "6abe54bf8bae596cbb1073f2"
         |  },
         |  "vrn": "100000222",
         |  "data": "qVctytyTS9wv269fBj+DUxFmo9xgMAzhjDPgRhVQPta+G//qapm3KPklj449oKbXc/oDokcZkbDeM61a+rsCj1mJdX10+1gwVCcJozbpuaZo9vawzj8R059/6UJh1hpBZBw8QwjldKT9XQMryDu7BRp/goJwJN44N4SAvjVGRZhu3TTzIYzPrGFXV65gMLFfHFwuUXJhj8j5HP43PEyVuMcAN/ikH0SzRklhYeSEADltwB5P+AHfbUdEow0FbEZEZzhhoNV6b5X8kjYRA9xIWQwL5CmzHEemgue1k0feuWBNA0lQjy+3eHWrGwd6qocyVD7ri/xv3+6WOhpQM7YG30MR4AL7cd+RwQqD5uUuswmcHDa9NB/cSAn92bNpbx2+vGpAeuu7B7FJSvHuIWaXtEN+xvQBRxB12csvWRQALhHBQUSYI94vmJM2+D4uBWlfICsQKpyjvwb+1ww7NullHxQBEK5/qHa6I/2KM5HsWQoVmMpEzMW/aRRDRybKmeiZP3eL2cXDu7nRnVM5iBfkqtdA0Rl0ZeQFAtEm+3dIRlMUz29yowR46E9To0IEAnq0kqPdcpE9lVDUD2LdDJZnNUeOruBEME2+li3W07lBoaW+YzAuS7KFixEsHMQsmCj13sN4JJfQH5Ya+UvdbaRArRWBIsANRo2agJHDubusr/e0lg==",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T12:40:31.962Z"
         |  }
         |}
         |""".stripMargin,
      s"""
         |{
         |  "_id": {
         |    "$$oid": "6abf65878bae596cbb45d329"
         |  },
         |  "vrn": "100000333",
         |  "data": "DOray2HCjaL40zpAlAW/ecD0V2ynK9sKjD1vTV+YcpbySVYaw8zVJBnLhYJUQ1VbBu5hgw4/WoDAapC6860/gqYdcSBaUPl9DnflEr3ZF6I5iTIVfV2RIrkQwaGrve4CIiXTs7cEFzolTG/oO5iBbwotCxe9rBBDAokHu+fY67V7ZxVcZKFNjExVCB1mA2Sx08KUXGSQ9O4sFgNoMlKsxrUmtb8es3xgNLPQEyPfvWsQhxOEiTGCfEL7diSUNZqCPL50iQf5SyrE6hTfPY9Rj3+F2MTMuhc09VDvuY/OBa3dgs5vpIiI6ALIwl96J4bZlH0C3LZ92ND3rYnTyGOaTTypKDDMwognXTgZeLROi/viZmXrnfcyS2Y2g5Qv7wpTUIurlC3L73ke1S1ZUn6PQgv1Mu+r3W/uNyvYRJsod/VInog/Ib7Yx143FOxyYxaJDJG9LKBwoP1Gt6LnvyaBxeYDUBQS4ZObbdOOQhSlYWw6Y64uLKNL9Q19KSzh35sww34Mux82nQb80teASxS8asbssIJT7Mw+ArMLWoFw0+0u1IpLC2uob5h5gMAYiOG50L+tZA6nj0kzDW7Yj0A3vuT8GujHw14I0AYBMxxkShtvyNEVAElXOoQQSBrU5M3E2VziA/hNSDNBcCT0stUSw615hF+7k6Auzx1m7p1t1Zm4cuzFt/c/hJJdG6JENl5eIFyU/7kVddSONIzuTASC2gjuRJaDj99+0CKcspJUMrGsPNg3oGAR/H2ZUEx4kV5stfv/0pcl20S9ovoImjN93fWngxByzlJPM/OZ0OuR1Q1wAPYaeEe2B2VCG/ax1ZVnLmbAvcy4t5YG2zpWgiEG9Kg=",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T08:04:23.377Z"
         |  }
         |}
         |""".stripMargin,
      s"""
         |{
         |  "_id": {
         |    "$$oid": "6abf669a8bae596cbb46edf3"
         |  },
         |  "vrn": "100000444",
         |  "data": "17JD7x9GuvoS91/o6fxr7X4EHbD2JJPoD2qwj5M2kZmi7iCnxQmq4GUywmeThRmxxfDFTPPGf91xbRmALE/RJKguY7fUQssA9C2b8D/GgRpUcaKZYjvMfWNpaGIK1lHQL2MdWqvPa2KvcoWWWravIe5fehz8PlZ4MHMKJylacsof76C3mHwCailAscbQ755ZqmcgqeBbYsS0RMz64RVMqJHhFtM3WUIUZFYqpIWU9mTIHQYCws8sUIOCcXuIDvMOlUc6hKDYYHRBg7CADVlwdRrnoguKwYSr6+Phg0oqlQ42nOmzwJw8DAIzU0CXrwAr3Bo9Jpq8rrsBvcP2rVf1q/81QyNglFTPlCi2qSMMhwlhoOwQaiKIwMiOcgEIHBye/zvQXTaq4vh8LEqn9RjTf9oKDtFtqIND5SB3u8k1aZakurDL4JyMrLKkg2iYU/YwIuOd+6f8vtJRjfbnZNE6FCTNMljDGpu9uXuZU99o4XMmtpsxbiUvcgXnuSnibjZQZ6Og2MJC9+UivZ4uqROOhlfi0EtyaJbNBgBcPVmfvOGgxpiOMZFLpdKBFaIlAd4T31IGRO3hi233Ha1ymhBoN7cErab+fNwMTjXw4t7VGaaHWY5oNEh6XkMD+NWO5vyHU8zoZfLsE3SGdUBCTUZvaKgCkGz1Wth1WOzUi7vJlXX/NsAxQbhVfTYdJMwd8JN4lTN7c7x5Tix/iLxE5GUy2uVdGDE52uIh6Hc6D5FOkq4eFNjGaHVCeVs+Wzw/5ABvODuyv5D5AZpy6bJxJfsBRZKdKAzhTaQWKSiSrhxfaFAIWC/K3LuThGW1Y+VFBJVXSiTuTAd5CdlPGzKYHw==",
         |  "lastUpdated": {
         |    "$$date": "${yesterday}T08:08:58.892Z"
         |  }
         |}
         |""".stripMargin
    )
}
