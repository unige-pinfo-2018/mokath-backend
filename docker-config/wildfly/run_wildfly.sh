#!/bin/bash

CONT_NAME="uniknowledge-wildfly"


echoerr() { echo "$1" 1>&2; }

mkdir /tmp/wildfly-deployments 1>/dev/null 2>/dev/null

docker network inspect uniknowledge-network 1>/dev/null 2>/dev/null
if [ $? -ne 0 ]; then
	docker network create --subnet 172.18.0.0/16   uniknowledge-network
fi

if [[ "$(docker images -q wildfly-prod 2> /dev/null)" == "" ]]; then
        docker build -t wildfly-prod .
fi


if [[ "$(docker ps -a -q -f "name=$CONT_NAME" 2> /dev/null)" != "" ]]; then
        docker start $CONT_NAME
else
	docker run -d --ip="172.18.0.4" --net="uniknowledge-network" -p 8080:8080 -p 9990:9990 -p 8787:8787 -v /tmp/wildfly-deployments:/opt/jboss/wildfly/standalone/deployments/ -v $PWD/wildfly:/opt/jboss/whildfly/standalone --name $CONT_NAME wildfly-prod

fi
