#!/bin/bash
javac -cp "test/junit-4.8.1.jar: test/hamcrest-2.2.jar:." test/*.java
java -cp "test/junit-4.8.1.jar: test/hamcrest-2.2.jar:." org.junit.runner.JUnitCore test.PremierLeagueManagerTest
java -cp "test/junit-4.8.1.jar: test/hamcrest-2.2.jar:." org.junit.runner.JUnitCore test.MatchTest
