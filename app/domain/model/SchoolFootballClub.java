package domain.model;
public class SchoolFootballClub extends FootballClub{
	static final long serialVersionUID = 55L;
	public static final int MAX_AGE = 18;

	private String schoolName;

	public SchoolFootballClub(String clubName, String country, String location, String schoolName) {
		super(clubName, country, location);
		this.schoolName = schoolName;
	}
	

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}
