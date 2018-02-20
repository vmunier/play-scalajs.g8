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

# produce archive with no source maps
sbt universal:packageBin
countScalaFiles
nbScalaFilesNoSourceMaps=$?

# produce archive with source maps
sbt "set emitSourceMaps in (client, fullOptJS) := true" "set emitSourceMaps in (sharedJs, fullOptJS) := true" universal:packageBin
countScalaFiles
nbScalaFilesWithSourceMaps=$?

echo "-- RESULTS --"
echo "Number of Scala files with source maps disabled: $nbScalaFilesNoSourceMaps (0 expected)"
echo "Number of Scala files with source maps enabled: $nbScalaFilesWithSourceMaps (>0 expected)"

[ "$nbScalaFilesNoSourceMaps" -eq "0" ] && [ "$nbScalaFilesWithSourceMaps" -gt "0" ]
