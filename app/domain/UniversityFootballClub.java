package domain;
public class UniversityFootballClub extends FootballClub {
	public static final int MAX_AGE = 23;

	private String universityName;

	public UniversityFootballClub(String country, String location, String clubName, String universityName) {
		super(country, location, clubName);
		this.universityName = universityName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
}
