package domain.entity;
public class SchoolFootballClub extends FootballClub{
	public static final int MAX_AGE = 18;

	private String schoolName;

	public SchoolFootballClub(String country, String location, String clubName, String schoolName) {
		super(country, location, clubName);
		this.schoolName = schoolName;
	}
	

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}
