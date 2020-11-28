package dao;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import utils.FileOperation;
import java.io.File;
import utils.ObjectOperation;
import domain.PremierLeagueManager;
import domain.model.Season;

public class PremierLeagueManagerDAO {
	private PremierLeagueManager plm;
	private final String databaseDir = "../.data/";
	private final String activeSeasonFilePath = databaseDir + "active-season";

	private PremierLeagueManagerDAO() {
		loadActivePremierLeagueManager();
	}
	public Optional<PremierLeagueManager> getPremierLeagueManagerBySeason(Season season) {
		String filePath = databaseDir + season.toString();
		ObjectOperation oo = new ObjectOperation();
		PremierLeagueManager plm = (PremierLeagueManager) oo.deserialize(filePath);
		return Optional.ofNullable(plm);
	}
	
	public PremierLeagueManager getPremierLeagueManager() {
		return plm;
	}
	public void save(PremierLeagueManager plm) {
		String filePath =  databaseDir + plm.SEASON.toString();
		ObjectOperation oo = new ObjectOperation();
		oo.serialize(filePath, plm);
	}
	public void loadActivePremierLeagueManager() {
		File activeSeason = new File(activeSeasonFilePath);
		Scanner sc = null;
			try {
				sc = new Scanner(activeSeason);
				String season = sc.nextLine();
				plm = getPremierLeagueManagerBySeason(Season.parse(season)).orElse(null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sc.close();
			}
	}
	public void logActiveSeason(String season) {
		FileOperation fo = new FileOperation(activeSeasonFilePath);
		fo.write(season);
	}
}
