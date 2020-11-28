package dao;
import utils.ObjectOperation;
import domain.PremierLeagueManager;
import domain.model.Season;

public class PremierLeagueManagerDAO {
	private static volatile PremierLeagueManagerDAO plmDAO;
	private static PremierLeagueManager plm;
	private static final String databaseDir = "../.data/";

	private PremierLeagueManagerDAO() { }

	public void initPremierLeagueManager(Season season) {
		String filePath = databaseDir + season.toString();
		ObjectOperation oo = new ObjectOperation();
		plm = (PremierLeagueManager) oo.deserialize(filePath);
		if(plm == null) {
			plm = new PremierLeagueManager(season);
		}
	}
	public PremierLeagueManager getPremierLeagueManager(){
		return plm;
	}
	public static PremierLeagueManagerDAO getInstance() {
		PremierLeagueManagerDAO _plmDAO = plmDAO;
		if(_plmDAO != null) {
			return plmDAO;
		}
		synchronized(PremierLeagueManagerDAO.class) {
			if(plmDAO == null) {
				plmDAO = new PremierLeagueManagerDAO();
			}
			return plmDAO;
		}
	}
	public void save(PremierLeagueManager plm) {
		String filePath =  databaseDir + plm.SEASON.toString();
		ObjectOperation oo = new ObjectOperation();
		oo.serialize(filePath, plm);
	}
}
