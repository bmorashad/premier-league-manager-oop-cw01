package cli;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dao.PremierLeagueManagerDAO;
import cli.custom.exception.NoMoreAttemptsLeft;
import domain.*;
import domain.model.FootballClub;
import domain.model.Season;
import domain.model.Match;
import domain.custom.exception.*;
import domain.custom.exception.SeasonFormatException;
import utils.Regex;
import utils.UpdateLogger;

public class StartLeagueManagerMenu {
	private static final int MAX_USER_INPUT_ATTEMPTS = 2;
	private static PremierLeagueManager plm;
	private static PremierLeagueManagerDAO plmDAO;
	private static Scanner sc;
	private static UpdateLogger ul;

	static {
		sc = new Scanner(System.in);
		plmDAO = PremierLeagueManagerDAO.getInstance();
		ul = new UpdateLogger("CLI");
	}
	private static boolean isOnlyAlphabet(String str) {
		return Regex.isMatch("^[aA-zZ ]+$", str);
	}
	private static boolean isEmpty(String str) {
		return str.trim().isEmpty();
	}
	// helper validate season input
	private static boolean isSeasonValid(Season season) {
		boolean isSeasonValid;
		int firstYear = season.getFirstYear();
		int secondYear = season.getSecondYear();
		boolean isYearDifferenceValid = secondYear - firstYear == 1;
		isSeasonValid = isYearDifferenceValid && secondYear <= (LocalDate.now().getYear() + 1);
		return isSeasonValid;
	}

    private static boolean confirm(String message) {
        System.out.println(message);
        String isConfirmed = sc.nextLine();
        if(isConfirmed.equalsIgnoreCase("y") || isConfirmed.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }
	
	public static void onStart() {
		GuiAppStarter.start(); 
	} 

	public static void listAvailableClubs() {
		List<FootballClub> footballClubs = plm.getAllClubs();
		int numOfClubs = footballClubs.size();
		String availableClubs = " ";
		for (int i = 0; i < numOfClubs; i++) {
			if(i != (numOfClubs - 1)) {
				availableClubs += footballClubs.get(i).getClubName() + " | ";
			} else {
				availableClubs += footballClubs.get(i).getClubName();
			}
		}
		System.out.println("---Available Clubs In League---");
		System.out.println(availableClubs);
	}
	public static void displayWelcomeMessage() {
		System.out.println("===============/|\\====================="); 
		System.out.println("   Welcome to Premier League Manager");
		System.out.println("=======================================");
	}
	public static void displayMenuInstructions() { 
		System.out.println("\u001B[1mOptions:\033[0;0m");
		System.out.println("     (a|A)\t- Add Football Club");
		System.out.println("     (r|R)\t- Remove Football Club");
		System.out.println("     (m|M)\t- Add Played Match");
		System.out.println("     (t|T)\t- Display Standings Table");
		System.out.println("     (s|S)\t- Display Statistics By Club");
		System.out.println("     (g|G)\t- Open Gui App");
		System.out.println("     (q|Q)\t- Exit Menu");
	}

	public static void addFootballClub() {
		try {
			String clubName = capitalize(getWord("Enter club name: "));
			String country = capitalize(getWord("Enter the country of club:  "));
			String location = capitalize(getWord("Enter club location: "));
			try {
				FootballClub footballClub = new FootballClub(clubName, country, location);
				boolean added = plm.addFootballClub(footballClub);
				if(added) {
					System.out.println("Successfully added the club to Premier League");	
					logFootballClubUpdate(footballClub, "CREATE");
					return;
				} 
				System.out.println("Club already exist");
			} catch (NoMoreClubsAllowed e) {
				System.out.println(e.getMessage());
			}
		} catch (NoMoreAttemptsLeft e) {
			System.out.println(e.getMessage());
		}
	}


	public static void removeFootballClub() {
		listAvailableClubs();
		System.out.print("Enter club name: ");
		String clubName = sc.nextLine();
		boolean isConfirm = confirm("Are you sure you want to delete the club(Y/N)? ");
		if(isConfirm) {
			FootballClub removedClub = plm.removeFootballClub(clubName);
			if(removedClub == null) {
				System.out.println("No such club to remove");
				return;
			}
			System.out.println(removedClub.getClubName() + " club successfully removed from Premier League!!");
			logFootballClubUpdate(removedClub, "DELETE");
			return;
		}
		System.out.println("Aborted!");
	}
	public static void displayStandingsTable() {
		List<FootballClub> footballClubs = plm.getAllClubs();
		Collections.sort(footballClubs, Collections.reverseOrder());
		// System.out.print("\u001B[1m");
		System.out.println("+----------------+--------+---------+-----------+------+------+------+-------+------+");
		System.out.println("|  Clubs         |  Wins  |  Draws  |  Defeats  |  GS  |  GA  |  GF  |  Pts  |  MP  |");
		System.out.println("+----------------+--------+---------+-----------+------+------+------+-------+------+");
		// System.out.print("\033[0;0m");

			
		footballClubs.stream().forEach(club -> {
			String clubName = compactWord(club.getClubName(), 12);
			
			System.out.printf("|%-16s|", "  " + clubName);
			System.out.printf("%s|", centerWord(club.getWinCount()+"", 8));
			System.out.printf("%s|", centerWord(club.getDrawCount()+"", 9));
			System.out.printf("%s|", centerWord(club.getDefeatCount()+"", 11));
			System.out.printf("%s|", centerWord(club.getGoalsScored()+"", 6));
			System.out.printf("%s|", centerWord(club.getGoalsAgainst()+"", 6));
			System.out.printf("%s|", centerWord(club.getGoalsDifference()+"", 6));
			System.out.printf("%s|", centerWord(club.getPoints()+"", 7));
			System.out.printf("%s|", centerWord(club.getMatchCount()+"", 6));
			System.out.print("\n");
		});
		System.out.println("+----------------+--------+---------+-----------+------+------+------+-------+------+");
	}
	// helper to compact long club names;
	private static String compactWord(String word, int maxLength) {
		if (word.length() > maxLength) {
			word = word.substring(0, maxLength-3);
			word = word.trim();
			word = word + "...";
		}
		return word;
	}
	private static String capitalize(String word) {
		String capitalizedWord = "";
		String[] words = word.split(" ");
		for (String str : words) {
			capitalizedWord += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		}
		return capitalizedWord.trim();
	}
	private static String centerWord(String word, int width) {
		if(word.length() > width) {
			word = compactWord(word, width);
		}
		String pad = " ";
		int widthDiff = width - word.length();
		int leftPadWidth = widthDiff/2;
		int rightPadWidth = widthDiff/2 + widthDiff%2;

		String leftPad = stringInto(pad, leftPadWidth); 
		String rightPad = stringInto(pad, rightPadWidth); 
		word = leftPad + word + rightPad;
		return word;
	}
	private static String stringInto(String str, int inTo) {
		String initialString = str;
		for(int i = 1; i < inTo; i++) {
			str += initialString;
		}
		return str;
	}

	public static void displayStatisticsByClub() {
		listAvailableClubs();
		System.out.print("Enter club name: ");
		String clubName = sc.nextLine();
		FootballClub footballClub = plm.getClubByName(clubName);
		if(footballClub == null) {
			System.out.println("No such club :(");
			return;
		}
		System.out.println("---------------------------------------");
		System.out.println("     \u001B[1mWins:\033[0;0m\t\t" + footballClub.getWinCount());
		System.out.println("     \u001B[1mDefeats:\033[0;0m\t\t" + footballClub.getDefeatCount());
		System.out.println("     \u001B[1mDraws:\033[0;0m\t\t" + footballClub.getDrawCount());
		System.out.println("     \u001B[1mPts:\033[0;0m\t\t" + footballClub.getPoints());
		System.out.println("     \u001B[1mGS:\033[0;0m\t\t" + footballClub.getGoalsScored());
		System.out.println("     \u001B[1mGA:\033[0;0m\t\t" + footballClub.getGoalsAgainst());
		System.out.println("     \u001B[1mGF:\033[0;0m\t\t" + footballClub.getGoalsDifference());
		System.out.println("     \u001B[1mMP:\033[0;0m\t\t" + footballClub.getMatchCount());
		System.out.println("---------------------------------------");
	}

	public static void addMatch(){
		listAvailableClubs();
		try {
			String teamA = getAvailableClubName("Enter Home team: ");
			String teamB  = getAvailableClubName("Enter Away team: ");
			if(teamA.equals(teamB)) {
				System.out.println("Given teams can not be opponent :(");
				return;
			}
			LocalDate date = getMatchDate("Enter match date(YYYY-MM-D): ");
			int teamAGoals = getGoals("Enter Home team goals: ");
			int teamBGoals = getGoals("Enter Away team goals: ");
			Match match = plm.addMatch(teamA, teamB, teamAGoals, teamBGoals, date);
			System.out.println("Match added successfully");
			logMatchUpdate(match, "CREATE");
		} catch(NoMoreAttemptsLeft e) {
			System.out.println(e.getMessage());
		}


	}

	private static Season getSeason(String label) throws NoMoreAttemptsLeft {
		int attempts = 0;
		Season season = null;
		while(attempts < MAX_USER_INPUT_ATTEMPTS) {
			try {
				System.out.println(label);
				season = Season.parse(sc.nextLine());
				if (isSeasonValid(season)) {
					attempts = MAX_USER_INPUT_ATTEMPTS;
				} else {
					System.out.println("Invalid season, try again..");
				}
			} catch(SeasonFormatException e) {
				System.out.println(e.getMessage());
				attempts += 1;
				if(attempts == MAX_USER_INPUT_ATTEMPTS) {
					throw new NoMoreAttemptsLeft("Sorry no more attempts left");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new NoMoreAttemptsLeft("Sorry no more attempts left");
			}
		}
		return season;
	}
	private static int getGoals(String label) throws NoMoreAttemptsLeft {
		int attempts = 0;
		int goals = 0;
		while(attempts < MAX_USER_INPUT_ATTEMPTS) {
			try {
				System.out.println(label);
				goals = Integer.parseInt(sc.nextLine());
				if(goals < 0) {
					System.out.println("Goals cannot be negative, try again");
					attempts += 1;
					if(attempts == MAX_USER_INPUT_ATTEMPTS) {
						throw new NoMoreAttemptsLeft("Sorry no more attempts left");
					}
				} else {
					break;
				}
			} catch(Exception e) {
				System.out.println("Invalid goal, try again");
				attempts += 1;
				if(attempts == MAX_USER_INPUT_ATTEMPTS) {
					throw new NoMoreAttemptsLeft("Sorry no more attempts left");
				}
			}
		}
		return goals;
	}
	private static String getWord(String label) throws NoMoreAttemptsLeft{
		int attempts = 0;
		String word = "";
		while(attempts < MAX_USER_INPUT_ATTEMPTS) {
			System.out.print(label);
			word = sc.nextLine();
			if(isOnlyAlphabet(word) && !isEmpty(word)) {
				return word;
			} else {
				System.out.println("Input can only contain alphabet!");
				attempts += 1;
				if(attempts == MAX_USER_INPUT_ATTEMPTS) {
					throw new NoMoreAttemptsLeft("Sorry no more attempts left");
				}
			}
		}
		return word;
	}
	private static LocalDate getMatchDate(String label) {
		int attempts = 0;
		LocalDate date = null;
		while(attempts < MAX_USER_INPUT_ATTEMPTS) {
			try {
				System.out.println(label);
				date = LocalDate.parse(sc.nextLine());
				if(!isMatchDateValid(date)) {
					System.out.println("Match date is not compatible with the current Premier League Season (" + plm.SEASON + ")");
					attempts += 1;
					if(attempts == MAX_USER_INPUT_ATTEMPTS) {
						throw new NoMoreAttemptsLeft("Sorry no more attempts left");
					}
				} else {
					break;
				}
			} catch(DateTimeParseException e) {
				System.out.println(e.getMessage());
				attempts += 1;
				if(attempts == MAX_USER_INPUT_ATTEMPTS) {
					throw new NoMoreAttemptsLeft("Sorry no more attempts left");
				}
			}
		}
		return date;
	}
	// match date validation
	private static boolean isMatchDateValid(LocalDate matchDate) {
		int firstLeagueYear = plm.SEASON.getFirstYear();
		int secondLeagueYear = plm.SEASON.getSecondYear();
		int matchYear = matchDate.getYear();
		if(matchYear == firstLeagueYear || matchYear == secondLeagueYear) {
			return true;
		}
		return false;
	}
	private static String getAvailableClubName(String label) {
		int attempts = 0;
		FootballClub club = null;
		while(attempts < MAX_USER_INPUT_ATTEMPTS) {
			System.out.print(label);
			String clubName = sc.nextLine();
			if(plm.getClubByName(clubName) != null) {
				return clubName;
			} else {
				System.out.println("No such club in the Premier League");
				attempts += 1;
				if(attempts == MAX_USER_INPUT_ATTEMPTS) {
					throw new NoMoreAttemptsLeft("Sorry no more attempts left");
				}
			}
		}

		return club;
	}
	public static void loadPremierLeague() {
		try {
			Season season = getSeason("Enter Premier League Season(YYYY-YYYY): ");
			plmDAO.initPremierLeagueManager(season);
			plm = plmDAO.getPremierLeagueManager();
			UpdateLogger.logActiveSeason(season.toString());
		} catch(NoMoreAttemptsLeft e) {
			System.out.println(e.getMessage());
		}
	}
	public static void savePremierLeague() {
		plmDAO.syncUpdates("gui");
		plmDAO.save(plm);
	}
	private static void logMatchUpdate(Match match, String updateType) {
		ul.logMatchUpdate(match, updateType);
	}
	private static void logFootballClubUpdate(FootballClub footballClub, String updateType) {
		ul.logFootballClubUpdate(footballClub, updateType);
	}
	public static void menu() {
		if(plm != null) {
			onStart();
			displayWelcomeMessage();
			boolean exit = false;
			while(!exit) {
				displayMenuInstructions();
				System.out.print("\nEnter an option: ");
				String option = sc.nextLine();
				plmDAO.syncUpdates("gui");
				switch(option.toLowerCase()) {
					case "a":
						addFootballClub();
						break;
					case "r":
						removeFootballClub();
						break;
					case "m":
						addMatch();
						break;
					case "t":
						displayStandingsTable();
						break;
					case "s":
						displayStatisticsByClub();
						break;
					case "g":
						System.out.println("Opening app...");
						GuiAppStarter.open();
						// GuiAppStarter.openApp();
						break;
					case "q":
						System.out.println("Saving current state of the Premier League...");
						savePremierLeague();
						System.out.println("Closing app connection...");
						GuiAppStarter.stop();
						System.out.println("Exiting menu...");
						exit = true;
						break;
					default:
						System.out.println("No such option :(\nTry again...\n");
				}
			}
		}
	}
	private static void cleanUp() {
		UpdateLogger.clearAllLogs();
		sc.close();
	}
	public static void main(String[] args) {
		try {
			loadPremierLeague();
			menu();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
	}
}
