package test;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import domain.PremierLeagueManager;
import domain.custom.exception.NoMoreClubsAllowed;
import domain.custom.exception.NoSuchClubException;
import domain.custom.exception.SeasonMismatchException;
import domain.model.FootballClub;
import domain.model.Season;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PremierLeagueManagerTest {

    private PremierLeagueManager plm;

    @Before
    public void init(){
        plm = new PremierLeagueManager(Season.parse("2020-2021"));
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testAddFootballClub(){
		boolean added = plm.addFootballClub(new FootballClub("Arsenal", "A", "A"));
		assertTrue(added);
		added = plm.addFootballClub(new FootballClub("Arsenal", "A", "A"));
		assertFalse(added);
    }
    @Test
    public void testAddFootballClubNoMoreClubsAllowdException() throws NoMoreClubsAllowed{
		for(int i = 0; i < plm.MAX_CLUBS; i++) {
			String clubName = "Arsenal" + i;
			plm.addFootballClub(new FootballClub(clubName, "country", "location"));
		}
		exception.expect(NoMoreClubsAllowed.class);
		plm.addFootballClub(new FootballClub("Juventus", "country", "location"));
    }
    @Test
    public void testAddMatch(){
		plm.addFootballClub(new FootballClub("Alien", "country", "location"));
		plm.addFootballClub(new FootballClub("Avatar", "country", "location"));
		plm.addFootballClub(new FootballClub("Manchester", "country", "location"));
		plm.addMatch("Alien", "Avatar", 4, 5, LocalDate.parse("2020-12-01"));
    }
    @Test
    public void testAddMatchNoSuchClubException() throws NoSuchClubException {
		plm.addFootballClub(new FootballClub("Alien", "country", "location"));
		plm.addFootballClub(new FootballClub("Avatar", "country", "location"));
		plm.addFootballClub(new FootballClub("Manchester", "country", "location"));

		exception.expect(NoSuchClubException.class);
		plm.addMatch("Juventus", "Avatar", 4, 5, LocalDate.parse("2020-12-01"));
    }
    @Test
    public void testAddMatchSeasonMismatchException() throws SeasonMismatchException { 
		plm.addFootballClub(new FootballClub("Alien", "country", "location"));
		plm.addFootballClub(new FootballClub("Manchester", "country", "location"));
		exception.expect(SeasonMismatchException.class);
		plm.addMatch("Alien", "Manchester", 4, 5, LocalDate.parse("2000-12-01"));
    }
	@Test
	public void testGetClubByName() {
		plm.addFootballClub(new FootballClub("Alien", "country", "location"));
		plm.addFootballClub(new FootballClub("Manchester", "country", "location"));
		FootballClub club = plm.getClubByName("Alien");
		assertNotNull(club);
		club = plm.getClubByName("Barca");
		assertNull(club);
	}
	@Test
	public void testRemoveClub() {
		plm.addFootballClub(new FootballClub("Alien", "country", "location"));
		plm.addFootballClub(new FootballClub("Manchester", "country", "location"));
		FootballClub club = plm.removeFootballClub("Alien");
		assertNotNull(club);
		assertEquals("Alien", club.getClubName());

		club = plm.removeFootballClub("Barca");
		assertNull(club);

		club = plm.getClubByName("Alien");
		assertNull(club);
		club = plm.getClubByName("Manchester");
		assertNotNull(club);
	}

}
