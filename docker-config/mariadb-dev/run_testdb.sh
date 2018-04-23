#!/bin/bash

if [[ "$(docker network ls -qf name=mariadb-network 2> /dev/null)" == "" ]]; then
        docker network create mariadb-network
fi

if [[ "$(docker images ls -qf name=mariadb-data 2> /dev/null)" == "" ]]; then
        docker volume create --name mariadb-data
fi


if [[ "$(docker ps -a -q -f "name=mariadb-test" 2> /dev/null)" != "" ]]; then
        docker start mariadb-test
else
				docker run -d -p 3306:3306 -v mariadb-data:/var/lib/mysql --net mariadb-network -e MYSQL_ROOT_PASSWORD=Pinfo2018 --name mariadb-test mariadb
fi

if [[ "$(docker ps -a -q -f "name=mariadb-test-admin" 2> /dev/null)" != "" ]]; then
  			docker start mariadb-test-admin
else
				docker run -d -p 8081:80 --net mariadb-network -e MYSQL_ROOT_PASSWORD=Pinfo2018 -e PMA_HOST="mariadb-test" -e PMA_PORT=3306 --name mariadb-test-admin phpmyadmin/phpmyadmin
fi
