package domain.model;
import java.io.Serializable;

public abstract class SportsClub implements Serializable{
	static final long serialVersionUID = 88L;
	protected String clubName;
	protected String country;
	protected String location;

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
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setClubName(String clubName) {
        this.clubName = clubName;
    } 
}
