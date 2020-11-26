import java.io.Serializable;
import java.time.LocalDate;

public class Match implements Serializable{
	private FootballClub teamA;
	private FootballClub teamB;
	private int teamAGoals;
	private int teamBGoals;
	private LocalDate date;
	private FootballClub winningTeam;
	private FootballClub defeatedTeam;
	private boolean draw;

	public Match(FootballClub teamA, FootballClub teamB, int teamAGoals, int teamBGoals, LocalDate date) {
		this.teamA = teamA;
		this.teamB = teamB;
		this.date = date;
		addGoals(teamAGoals, teamBGoals);
	}

	public FootballClub getTeamA() {
		return teamA;
	}

	public FootballClub getTeamB() {
		return teamB;
	}

	public int getTeamAGoals(){
		return teamAGoals;
	}

	public int getTeamBGoals(){
		return teamBGoals;
	}

	public LocalDate getDate() {
		return date;
	}

	public FootballClub getWinningTeam() {
		return winningTeam;
	}

	public FootballClub getDefeatedTeam() {
		return defeatedTeam;
	}

	public boolean isDraw() {
		return draw;
	}
	public void setTeamA(FootballClub teamA) {
		this.teamA = teamA;
	}

	public void setTeamB(FootballClub teamB) {
		this.teamB = teamB;
	}



	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void addGoals(int teamAGoals, int teamBGoals) {
		this.teamAGoals = teamAGoals;
		this.teamBGoals = teamBGoals;
		draw = false;
		if (teamAGoals > teamBGoals) {
			winningTeam = teamA;
			defeatedTeam = teamB;
			return;
		} 
		if(teamAGoals < teamBGoals) {
			winningTeam = teamB;
			defeatedTeam = teamA;
			return;
		}
		draw = true;
	}

}
