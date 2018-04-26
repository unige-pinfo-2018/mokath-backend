#!/bin/bash

# from https://github.com/goldmann/wildfly-docker-configuration/blob/master/cli/customization/execute.sh

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -c $JBOSS_CONFIG > /dev/null &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
export UNIKNOWLEDGE_DS="java:/UniknowledgeDS"
export MYSQL_URI="jdbc:mariadb://172.18.0.3:3306/uniknowledge"
export MYSQL_USER="uni-user"
export MYSQL_PWD="Uniknowledge-2018"

$JBOSS_CLI -c << EOF
batch

echo "Connection URL: " $CONNECTION_URL

# Add MySQL module
module add --name=com.mysql --resources=/opt/jboss/wildfly/mysql-connector-java-5.1.32.jar --dependencies=javax.api,javax.transaction.api

# Add MySQL driver
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)

# Add the datasource
data-source add --name=StudentsDS --driver-name=mysql --jndi-name=$UNIKNOWLEDGE_DS --connection-url=$MYSQL_URI?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=$MYSQL_USER --password=$MYSQL_PWD --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000

# Execute the batch
run-batch
EOF

echo "=> Executing the commands"
$JBOSS_CLI -c --file=`dirname "$0"`/commands.cli
$JBOSS_HOME/bin/add-user.sh -u "admin" -p "pinfo2018" -s

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi
