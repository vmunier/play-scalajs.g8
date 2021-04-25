#!/bin/sh

baseDir="$( cd "$( dirname "$0" )"/.. && pwd )"
g8TemplateOutput=$baseDir/target/g8

countScalaFiles() {
  archive="$g8TemplateOutput/server/target/universal/server-0.1.0-SNAPSHOT.zip"
  unzip -o $archive
  nbScalaFiles=$(unzip -l "server-0.1.0-SNAPSHOT/lib/*server*.jar" | grep ".*\.scala$" | wc -l)
  return "$nbScalaFiles"
}

cd $baseDir

# Apply default parameters to input templates and write to target/g8.
sbt clean g8

cd $g8TemplateOutput

# run test
sbt test
testResult=$?
if [ "$testResult" -ne 0 ]; then
   echo >&2 "sbt test failed"
   exit $testResult
fi

# produce archive with source maps
sbt "set Global / scalaJSStage := FullOptStage" Universal/packageBin
countScalaFiles
nbScalaFilesWithSourceMaps=$?

# produce archive with no source maps
sbt "set Global / scalaJSStage := FullOptStage" "set ThisBuild / Compile / fullOptJS / scalaJSLinkerConfig ~= (_.withSourceMap(false))" Universal/packageBin
countScalaFiles
nbScalaFilesNoSourceMaps=$?

echo "-- RESULTS --"
echo "Number of Scala files with source maps enabled: $nbScalaFilesWithSourceMaps (>0 expected)"
echo "Number of Scala files with source maps disabled: $nbScalaFilesNoSourceMaps (0 expected)"

[ "$nbScalaFilesWithSourceMaps" -gt "0" ] && [ "$nbScalaFilesNoSourceMaps" -eq "0" ]
