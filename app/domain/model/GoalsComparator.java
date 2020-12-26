package domain.model;
import java.util.Comparator;

public class GoalsComparator implements Comparator<FootballClub> {
	@Override
	public int compare(FootballClub club1, FootballClub club2) {
		return (club1.getGoalsScored() - club2.getGoalsScored());
	}
}
