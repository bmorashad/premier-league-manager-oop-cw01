package domain.model;
import java.util.Comparator;

public class PointsComparator implements Comparator<FootballClub> {
	@Override
	public int compare(FootballClub club1, FootballClub club2) {
		return (club1.getPoints() - club2.getPoints());
	}
}
