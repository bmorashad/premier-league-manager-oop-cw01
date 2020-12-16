package domain.model;
import java.io.Serializable;
import java.time.LocalDate;
import domain.custom.exception.NoOpponentFoundException;

public class Match implements Serializable{
	static final long serialVersionUID = 66L;

	private FootballClub teamA;
	private FootballClub teamB;
	private int teamAGoals;
	private int teamBGoals;
	private LocalDate date;
	private FootballClub winningTeam;
	private FootballClub defeatedTeam;

	public Match(FootballClub teamA, FootballClub teamB, int teamAGoals, int teamBGoals, LocalDate date) throws NoOpponentFoundException {
		if(teamA.equals(teamB)) {
			throw new NoOpponentFoundException("TeamA and TeamB cannot be same!");
		}
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

	public void setDate(LocalDate date) {
		this.date = date;
	}

	private void addGoals(int teamAGoals, int teamBGoals) {
		this.teamAGoals = teamAGoals;
		this.teamBGoals = teamBGoals;
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
	}

}
