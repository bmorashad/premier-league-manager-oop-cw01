#!/bin/bash
javac -sourcepath ./ -d out -classpath out cli/StartLeagueManagerMenu.java
java -cp cli/StartLeagueManagerMenu

# rm -rf domain/model/*.class
# rm -rf domain/custom/exception/*.class
# rm -rf domain/*.class
# rm -rf utils/*.class
# rm -rf dao/*.class
# rm -rf cli/custom/exception/*.class
# rm -rf cli/*.class
# rm -rf conf/*class
# rm -rf dto/*class
# javac cli/StartLeagueManagerMenu.java
# java cli/StartLeagueManagerMenu
