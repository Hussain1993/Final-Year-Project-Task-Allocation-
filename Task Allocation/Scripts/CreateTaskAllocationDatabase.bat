@ECHO off
set WORKING_DIR=%CD%
set SQL_FILE_NAME=TaskAllocationDatabase.sql
set CREATE_DATABASE=%CD%\%SQL_FILE_NAME%

if not exist %CREATE_DATABASE% (
	echo The script to create the Task Allocation database was not found
	exit
	)
set /p MYSQL_PATH=Enter the path to the MySQL executable, followed by [ENTER]: 

set /p MYSQL_USERNAME=Enter the username used to login to MySQL, followed by [ENTER]:

set /p MYSQL_PASSWORD=Enter the password used to login to MySQL, followed by [ENTER]:

%MYSQL_PATH% -u%MYSQL_USERNAME% -p%MYSQL_PASSWORD% -e "source %CREATE_DATABASE%"

echo Finished