package domain.model;
import java.io.Serializable;

public abstract class SportsClub implements Serializable{
	static final long serialVersionUID = 88L;
	String clubName;
	String country;
	String location;

	public String getClubName() {
        return clubName;
    }

	public String getCountry() {
		return country;
	}
    
    public String getLocation() {
        return location;
    }
	public void setCountry(String country) {
		this.country = country;
	}
    public void setClubLocation(String s) {
        this.location = s;
    }
    
    public void setClubName(String clubName) {
        this.clubName = clubName;
    } 
}
