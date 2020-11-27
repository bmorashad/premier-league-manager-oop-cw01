package dto;
import java.io.Serializable;
import java.time.LocalDate;

public class MatchDTO{
	String teamA;
	String teamB;
	int teamAGoals;
	int teamBGoals;
	LocalDate date;

	public MatchDTO(String teamA,String teamB, int teamAGoals, int teamBGoals, LocalDate date) {
		this.teamA = teamA;
		this.teamB = teamB;
		this.teamAGoals = teamAGoals;
		this.teamBGoals = teamBGoals;
		this.date = date;
	}

	public String getTeamA() {
		return teamA;
	}
	public String getTeamB() {
		return teamB;
	}
	public int getTeamAGoals() {
		return teamAGoals;
	}
	public int getTeamBGoals() {
		return teamBGoals;
	}
	public LocalDate getDate() {
		return date;
	}
}
