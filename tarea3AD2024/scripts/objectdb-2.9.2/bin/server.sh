#!/bin/sh
# 
# ObjectDB Server Script - for Linux / Mac OS X
# 
# Note: Please set the JAVA_VM and OBJECTDB_JARS variables!

# Path of Java VM (a complete absolute path can be specified)
JAVA_VM="java"

# Path of ObjectDB jar file (a complete absolute path can be specified)
OBJECTDB_JARS="./*"

# Prepare args (an input odb file if specified)
while [ $# -gt 0 ]; do
  ARGS="$ARGS $1"
  shift
done

# Launch ObjectDB Server:
exec ${JAVA_VM} -server -Xms64M -Xmx512M -cp "${OBJECTDB_JARS}" com.objectdb.Server $ARGS
