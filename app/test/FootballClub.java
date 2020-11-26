public class FootballClub extends SportsClub implements Comparable<FootballClub> {
	private static final long serialVersionUID = 6L;

	private int winCount;
	private int drawCount;
	private int defeatCount;
	private int matchCount;
	private int goalsAgainst;
	private int goalsScored;
	private int goalsDifference;
	private int points;

	public FootballClub(String clubName, String country, String location) {
		this.clubName = clubName;
		this.country = country;
		this.location = location;
	}

	public int getWinCount() {
		return winCount;
	}

	public int getDrawCount() {
		return drawCount;
	}

	public int getDefeatCount() {
		return defeatCount;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public int getGoalsScored() {
		return goalsScored;
	}

	public int getGoalsDifference() {
		return goalsDifference;
	}

	public int getPoints() {
		return points;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public void setDefeatCount(int defeatCount) {
		this.defeatCount = defeatCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}
	public void setGoalsScored(int goalsScored) {
		this.goalsScored = goalsScored;
	}
	public void setGoalsDifference(int goalsDifference) {
		this.goalsDifference = goalsDifference;
	}
	public void setPoints(int points) {
		this.points = points;
	}

	// controversial
	public void addWinningMatch() {
		matchCount += 1;
		points += 3;
		winCount += 1;
	}
	public void addDefeatedMatch() {
		matchCount += 1;
		defeatCount += 1;
	}
	public void addDrawMatch() {
		matchCount += 1;
		points += 1;
		drawCount += 1;
	}
	public void addGoals(int goalsScored, int goalsAgainst) {
		this.goalsScored += goalsScored;
		this.goalsAgainst += goalsAgainst;
		goalsDifference = this.goalsScored - this.goalsAgainst;
	}
	@Override
    public boolean equals(Object o) {
        return this.clubName.equals(((FootballClub)o).clubName);
    }

	@Override
	public int compareTo(FootballClub footballClub) {
		PointsComparator pc = new PointsComparator();
		GoalsComparator gc = new GoalsComparator();
		int comparisonResult = pc.compare(this, footballClub);
		if(comparisonResult == 0) {
			comparisonResult = gc.compare(this, footballClub);
		}
		return comparisonResult;

	}
}

