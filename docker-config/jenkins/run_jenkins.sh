#!/bin/bash

CONT_NAME="jenkins"


echoerr() { echo "$1" 1>&2; }

if [[ "$(docker images -q jenkins-prod 2> /dev/null)" == "" ]]; then
        docker build -t jenkins-prod .
fi


if [[ "$(docker ps -a -q -f "name=$CONT_NAME" 2> /dev/null)" != "" ]]; then
        docker start $CONT_NAME
else
	docker run -d -p 8181:8080 -u root -v /tmp/wildfly-deployments:/tmp/wildfly-deployments -v /var/run/docker.sock:/var/run/docker.sock -v /home/docker-master/jenkins_home:/var/jenkins_home --name $CONT_NAME jenkins-prod

fi
