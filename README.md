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

## Launching the test database locally

You can launch a test database (MariaDB) and a web interface (phpMyAdmin) with data persistence locally using the following steps :

1. Make sure you have running on your computer
2. Go to docker-config/mariadb-test/
3. Run the script run_testdb.sh with a user having docker rights
4. Go to http://localhost:8080 to connect to phpMyAdmin (user = root , password = Pinfo2018)
