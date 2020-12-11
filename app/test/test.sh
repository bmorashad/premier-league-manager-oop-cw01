#!/bin/bash

javac -cp "junit-4.13.1.jar: hamcrest-1.3.jar:." *.java
java -cp "junit-4.13.1.jar: hamcrest-1.3.jar:." org.junit.runner.JUnitCore PremierLeagueManagerTesting
