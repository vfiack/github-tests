#!/bin/sh

echo "listing home files"
ls $HOME

echo "listing current files"
ls

echo "cat files.txt"
cat $HOME/files.txt

# get all changed files from command line argument
# then extract and deduplicate their root folder
# filter those containing a pom.xml file
# and finally run sonar on those
cat $HOME/files.txt | cut -d '/' -f 1 | sort | uniq | while read MODULE; do
	POM=$MODULE/pom.xml
	if [ -f $POM ]; then
		echo "*** ANALYZING " $MODULE
		mvn -f $POM -B org.sonarsource.scanner.maven:sonar-maven-plugin:sonar
	fi
done
