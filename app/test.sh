#!/bin/bash
# rm test/*.class
rm -rf domain/model/*.class
rm -rf domain/custom/exception/*.class
rm -rf domain/*.class
rm -rf utils/*.class
rm -rf dao/*.class
rm -rf cli/custom/exception/*.class
rm -rf cli/*.class
rm -rf conf/*class
rm -rf dto/*class
javac cli/StartLeagueManagerMenu.java # to compile all the classes
javac -cp "test/junit-4.8.1.jar: test/hamcrest-2.2.jar:." test/*.java
java -cp "test/junit-4.8.1.jar: test/hamcrest-2.2.jar:." org.junit.runner.JUnitCore test.PremierLeagueManagerTest
java -cp "test/junit-4.8.1.jar: test/hamcrest-2.2.jar:." org.junit.runner.JUnitCore test.MatchTest
