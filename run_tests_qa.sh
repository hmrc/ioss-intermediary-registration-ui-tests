#!/bin/bash -e

sbt clean -Dbrowser="chrome" -Denvironment="qa" -Daccessibility.timeout="1000" "testOnly uk.gov.hmrc.ui.specs.MainTests.*" testReport
