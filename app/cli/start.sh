#!/bin/bash

if [ -f out/StartLeagueManagerMenu.class ] 
then
	rm out/*.class
fi
export CLASSPATH=
javac *.java -d out/
export CLASSPATH=out/
java StartLeagueManagerMenu
