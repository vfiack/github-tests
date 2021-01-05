#!/bin/sh

# generated by trilom/file-changes-action
# contains space-separated paths for all changed files
INPUT=$HOME/files.txt

# when the input does not exist, it means that file-change-actions was not executed
# this is normal for push triggers - in this case, analyse all modules
if [ ! -f $INPUT ]; then
  echo "*** ANALYZING all modules ***"
  ls > $INPUT
fi

# split files by space then extract and deduplicate their root folder
# filter those containing a pom.xml file
# and finally run sonar on those
cat $INPUT | tr " " "\n" | cut -d '/' -f 1 | sort | uniq | while read MODULE; do
	POM=$MODULE/pom.xml
	if [ -f $POM ]; then
		echo "*** ANALYZING " $MODULE " ***"
		mvn -f $POM -B org.sonarsource.scanner.maven:sonar-maven-plugin:sonar
	fi
done
