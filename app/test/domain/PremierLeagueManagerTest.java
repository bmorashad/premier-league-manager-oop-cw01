package test.domain;

import org.junit.*;

import domain.PremierLeagueManager;
import domain.model.Season;

import static org.junit.Assert.*;

public class PremierLeagueManagerTest {

    private PremierLeagueManager plm;

    @Before
    public void init(){
        plm = new PremierLeagueManager(Season.parse("2020-2021"));
    }

    @Test
    public void testingAddition(){
        assertEquals(5, 5);
    }

}
