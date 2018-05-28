#!/bin/bash

CONT_NAME="uniknowledge-elasticsearch"
IMG_NAME="uniknowledge-elasticsearch"
NETWORK_NAME="uniknowledge-network"


if [[ "$(docker network ls -q -f name=$NETWORK_NAME 2> /dev/null)" == "" ]]; then
	docker network create --subnet 172.18.0.0/16   $NETWORK_NAME
fi

if [[ "$(docker images -q $IMG_NAME 2> /dev/null)" == "" ]]; then
        docker build -t $IMG_NAME .
fi


if [[ "$(docker ps -a -q -f "name=$CONT_NAME" 2> /dev/null)" != "" ]]; then
        docker start $CONT_NAME
else
	docker run -d --ip="172.18.0.5" --net="$NETWORK_NAME" -v es-data:/usr/share/elasticsearch/data -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --name $CONT_NAME $IMG_NAME
fi
