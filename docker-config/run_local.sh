#!/bin/bash

echo "Launching local database..."
cd ./database
source run_db.sh
cd ..

echo "Launching local wildfly instance..."
cd ./wildfly
source run_wildfly.sh
cd ..

echo "Launching local elasticsearch instance..."
cd ./elasticsearch
source run_es.sh
cd ..
