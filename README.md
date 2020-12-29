# Sample project with multi-module (monorepo) sonar integration, using jacoco for code coverage.

## monorepo setup

The project must be created as a monorepo in sonar (this works only for github & azure, we're lucky here).
Then all individual modules must be created as projects in the monorepo in sonar.

New modules can be added later, but it is a bit strange:
* go on the "+" icon on top right,  "analyze new project"
* click on "configure monorepo" if visible near the repository name, otherwise,
* click on setup on monorepo (on the bottom right)
    - select the repository
    - then finally Add new project

## analyzing modules

Each module must be analyzed as a separate project. The parent project must not be analyzed.
The project keys must be given when adding the project to sonar's monorepo config, and must match the pom.xml property:
<sonar.projectKey>vfiack_github-tests:${project.artifactId}</sonar.projectKey>


Examples in workflows/maven.yml:
$ mvn -f ./base/pom.xml -B org.sonarsource.scanner.maven:sonar-maven-plugin:sonar
$ mvn -f ./secondary/pom.xml org.sonarsource.scanner.maven:sonar-maven-plugin:sonar

using a shells script with find like our jenkins pipeline would be better
    
## adding coverage

Jacoco is needed for coverage information. 
Two steps are needed (see workflows/maven.yml):

$ mvn org.jacoco:jacoco-maven-plugin:prepare-agent test -Dmaven.test.failure.ignore=true
$ mvn org.jacoco:jacoco-maven-plugin:report

Then, the path must be set in pom.xml properties so that sonar can find it:
<sonar.coverage.jacoco.xmlReportPaths>**/target/site/jacoco</sonar.coverage.jacoco.xmlReportPaths>
