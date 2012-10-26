#!/bin/bash

if [ $# -lt 1 ];
then
  echo "USAGE: $0 classname [opts]"
  exit 1
fi

base_dir=$(dirname $0)/..

CLASSPATH=$CLASSPATH:$base_dir/conf


if [ -z "$JAVA_OPTS" ]; then
  JAVA_OPTS="-Djava.ext.dirs=$base_dir/lib "
fi

if [ -z "$JAVA_HOME" ]; then
  JAVA="java"
else
  JAVA="$JAVA_HOME/bin/java"
fi

$JAVA ${JAVA_OPTS} -classpath $CLASSPATH $@