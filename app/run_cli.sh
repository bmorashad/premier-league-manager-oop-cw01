rm -f dao/*.class
rm -f cli/*.class
rm -f conf/*.class
rm -f utils/*.class
rm -f domain/*.class
rm -f domain/model/*.class
rm -f cli/custom/exception/*.class
rm -f domain/custom/exception/*.class
javac -d out/ cli/StartLeagueManagerMenu.java
java -cp out/ cli/StartLeagueManagerMenu
