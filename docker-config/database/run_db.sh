#!/bin/bash

CONT_NAME="uniknowledge-db"
IMG_NAME="uniknowledge-mysql"
NETWORK_NAME="uniknowledge-network"
DATABASE="uniknowledge"
DB_USER="uni-user"

# Remove when docker secret is up
PW="Pinfo2018"
USER_PW="Uniknowledge-2018"

if [[ "$(docker network ls -q -f name=$NETWORK_NAME 2> /dev/null)" == "" ]]; then
	docker network create --subnet 172.18.0.0/16   $NETWORK_NAME
fi

if [[ "$(docker images -q $IMG_NAME 2> /dev/null)" == "" ]]; then
        docker build -t $IMG_NAME .
fi


if [[ "$(docker ps -a -q -f "name=$CONT_NAME" 2> /dev/null)" != "" ]]; then
        docker start $CONT_NAME
else
	docker run -d --ip="172.18.0.3" --net="$NETWORK_NAME" -v uniknowledge-mysql-data:/var/lib/mysql -p 3306:3306 -e MYSQL_DATABASE=$DATABASE -e MYSQL_USER=$DB_USER -e MYSQL_PASSWORD=$USER_PW -e MYSQL_ROOT_PASSWORD=$PW --name $CONT_NAME $IMG_NAME
fi

if [[ "$(docker ps -a -q -f "name=uniknowledge-admin" 2> /dev/null)" != "" ]]; then
        docker start uniknowledge-admin
else
	docker run -d -p 8081:80 --net uniknowledge-network -e MYSQL_ROOT_PASSWORD=Pinfo2018 -e PMA_HOST="uniknowledge-db" -e PMA_PORT=3306 --name uniknowledge-admin phpmyadmin/phpmyadmin
fi
