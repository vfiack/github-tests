#!/bin/sh

# get all changed files from command line argument
# then extract and deduplicate their root folder
# filter those containing a pom.xml file
# and finally run sonar on those
for FILE in $*; do
	echo $FILE | cut -d '/' -f 1
done | sort | uniq | while read MODULE; do
	POM=$MODULE/pom.xml
	if [ -f $POM ]; then
		mvn -f $POM -B org.sonarsource.scanner.maven:sonar-maven-plugin:sonar
	fi
done

