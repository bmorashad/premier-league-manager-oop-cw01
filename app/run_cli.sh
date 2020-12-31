#!/bin/bash
rm -rf out/*
javac -d out/ cli/StartLeagueManagerMenu.java
java -cp out/ cli/StartLeagueManagerMenu
