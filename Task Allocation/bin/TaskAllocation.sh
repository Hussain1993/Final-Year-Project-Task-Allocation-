#!/bin/bash

java -Dtask.allocation.properties.file=./config/TaskAllocation.properties -Dlog4j.xml=./config/log4j.xml -jar ./TaskAllocation.jar
