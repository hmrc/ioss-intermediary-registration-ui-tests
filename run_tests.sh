#!/bin/bash -e

sbt clean -Dbrowser="chrome" -Denvironment="local" "testOnly uk.gov.hmrc.ui.specs.*" testReport
