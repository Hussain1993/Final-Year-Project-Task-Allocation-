#!/bin/sh

WORKING_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
SQL_FILE_NAME="TaskAllocationDatabase.sql"
CREATE_DATABASE="${WORKING_DIR}/${SQL_FILE_NAME}"

if [ ! -f  "${CREATE_DATABASE}" ]; then
    echo "The script to create the Task Allocation database was not found"
    exit
fi

echo "Enter the path to the MySQL executable, followed by [ENTER]: "
read MYSQL_PATH

echo "Enter the username used to login to MySQL, followed by [ENTER]: "
read MYSQL_USERNAME

${MYSQL_PATH} -u${MYSQL_USERNAME} -p -e "source ${CREATE_DATABASE}"

echo "Finished"


