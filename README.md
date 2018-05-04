# MOKATH UniKnowledge Backend Repository

You'll find here all administration sources and config for the backend part of UniKnowledge project by MOKATH Team.

## How to use git ?

### Before coding anything

1. Create a new branch from `develop` with a name describing the feature you're adding : e.g."auth-service-base"

    1.1 `git checkout develop`

    1.2 `git checkout -b new-branch-name`

PLEASE MAKE SURE YOU CREATE THE BRANCH FROM `develop`

2. You can ensure that you're on the correct branch with the following command :
`git branch`

The branch you're actually working on is marked as : `* current-branch`

3. To commit and push your changes to your branch :

    3.1 Ensure you're on the right branch : `git branch` it SHOULD NOT be `develop` or `master`

    3.2 `git add -A`

    3.3 `git commit -m "Describe your changes"`

    3.4 `git push`

## Launching the local dev environment :

You can launch a test database (MySQL), a web interface (phpMyAdmin) with data persistence and a wildfly instance locally using the following steps :

1. Make sure you have docker running on your computer
2. Go to docker-config/
3. Run the script run_local.sh with a user having docker rights
4. Make sure you have the following containers up by running `docker ps` :
	- uniknowledge-admin
	- uniknowledge-db
	- uniknowledge-wildfly

Note that the wildfly container maps `/tmp/wildfly-deployments` on the host with the autodeploy folder of the wildfly instance.

The default ports are :
- 8080 : wildfly
- 8081 : phpMyAdmin
- 3306 : MySQL

Ports can be modified as needed in the individuals bash scripts. But if you do so be sure **NOT TO COMMIT** those changes!

## Maven Commands

1. Build and Deploy WARs
`mvn clean install`

2. Clean and remove WARs
`mvn clean`

3. Update dependencies versions to use latest releases in pom.xml
`mvn versions:use-latest-releases`
