package domain.model;
import java.io.Serializable;
import utils.Regex;
import domain.custom.exception.SeasonFormatException;

public class Season implements Serializable{
	private static final long serialVersionUID = 9L;

	public static final String SEASON_FORMAT = "YYYY-YYYY";

	private int firstYear;
	private int secondYear;

	public Season(int firstYear, int secondYear) {
		this.firstYear = firstYear;
		this.secondYear = secondYear;
	}

	public int getFirstYear() {
		return firstYear;
	}
	public int getSecondYear() {
		return secondYear;
	}
	public void setFirstYear(int firstYear) {
		this.firstYear = firstYear;
	}
	public void setSecondYear(int secondYear) {
		this.secondYear = secondYear;
	}

	public static Season parse(String season) throws SeasonFormatException {
		boolean isSeasonFormatValid = Regex.isMatch("([1-9])[0-9]{3}-([1-9])[0-9]{3}", season);
		if(!isSeasonFormatValid) {
			throw new SeasonFormatException("SeasonFormat Exception: valid format(" + SEASON_FORMAT + ")");
		}
		String[] years  = season.split("-");
		Integer firstYear = Integer.parseInt(years[0]);
		Integer secondYear = Integer.parseInt(years[1]);
		return new Season(firstYear, secondYear);
	}

	@Override
	public String toString() {
		return firstYear + "-" + secondYear;
	}

}
