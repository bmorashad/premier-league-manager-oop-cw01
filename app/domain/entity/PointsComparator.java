package domain.entity;
import java.util.Comparator;

public class PointsComparator implements Comparator<FootballClub> {
	@Override
	public int compare(FootballClub club1, FootballClub club2) {
		if(club1.getPoints() > club2.getPoints()) {
			return 1;
		} else if(club1.getPoints() < club2.getPoints()) {
			return -1;
		} else {
			return 0;
		}
	}
}
