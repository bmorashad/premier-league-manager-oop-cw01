package test;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import domain.custom.exception.NoOpponentFoundException;
import domain.model.FootballClub;
import domain.model.Match;

import static org.junit.Assert.*;

import java.time.LocalDate;

public class MatchTest {

    private Match match;
	private	FootballClub clubA;
	private	FootballClub clubB;
	private	FootballClub clubC;

    @Before
    public void init(){
		clubA = new FootballClub("Arsenal", "A", "A");
		clubB = new FootballClub("Liverpool", "A", "A");
		clubC = new FootballClub("Barca", "A", "A");
    }
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testMatchConstruction() throws NoOpponentFoundException {
		match = new Match(clubA, clubB, 4, 5, LocalDate.parse("2020-02-20"));
		assertNotNull(match);
		exception.expect(Exception.class);
		match = new Match(clubA, clubA, 4, 5, LocalDate.parse("2020-02-20"));
    }
}
