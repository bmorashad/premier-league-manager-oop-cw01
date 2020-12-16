package domain.model;
public class UniversityFootballClub extends FootballClub {
	static final long serialVersionUID = 77L;
	public static final int MAX_AGE = 23;

	private String universityName;

	public UniversityFootballClub(String clubName, String country, String location, String universityName) {
		super(clubName, country, location);
		this.universityName = universityName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
}
