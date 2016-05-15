
package lionel.demos.sportscore.util.tennis;

import lionel.demos.sportscore.model.tennis.TennisGame;
import lionel.demos.sportscore.model.tennis.TennisGameTests;
import lionel.demos.sportscore.model.tennis.TennisPlayer;
import lionel.demos.sportscore.model.tennis.TennisScore;
import lionel.demos.sportscore.model.tennis.TennisScore.TennisPlayerScore;
import lionel.demos.sportscore.model.tennis.TennisSet;
import lionel.demos.sportscore.model.tennis.TennisSetTests;
import lionel.demos.sportscore.model.tennis.TennisWinningStrategy.PointsHolder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author lionel.ngounou
 */
public class TennisMatchManagerTests {
        
    @Test
    public void getPointsHolder_Game(){
        TennisGame game = TennisGameTests.get();
        PointsHolder ph = TennisMatchManager.getPointsHolder(game);
        assertTrue(ph.getTotal()==0);
        game.addPoint(game.getPlayerOne());
        ph = TennisMatchManager.getPointsHolder(game);
        assertTrue(ph.getPointOne()==1);
        assertTrue(ph.getPointTwo()==0);
        game.addPoint(game.getPlayerOne()).addPoint(game.getPlayerTwo());
        ph = TennisMatchManager.getPointsHolder(game);
        assertTrue(ph.getPointOne()==2);
        assertTrue(ph.getPointTwo()==1);
        game.addPoint(game.getPlayerOne()).addPoint(game.getPlayerTwo()).addPoint(game.getPlayerOne());
        ph = TennisMatchManager.getPointsHolder(game);
        assertTrue(ph.getPointOne()==4);
        assertTrue(ph.getPointTwo()==2);        
    }
    
    @Test
    public void isDeuce() {
        TennisGame game = TennisGameTests.get();
        assertFalse(TennisMatchManager.isDeuce(game));
        game.addPoint(game.getPlayerOne()).addPoint(game.getPlayerTwo());  
        game.addPoint(game.getPlayerOne()).addPoint(game.getPlayerTwo());     
        assertFalse(TennisMatchManager.isDeuce(game)); 
        game.addPoint(game.getPlayerOne()).addPoint(game.getPlayerTwo());     
        assertTrue(TennisMatchManager.isDeuce(game));
        game.addPoint(game.getPlayerOne()).addPoint(game.getPlayerTwo());     
        assertTrue(TennisMatchManager.isDeuce(game));
        game.addPoint(game.getPlayerOne());     
        assertFalse(TennisMatchManager.isDeuce(game));
        game.addPoint(game.getPlayerTwo());     
        assertTrue(TennisMatchManager.isDeuce(game));
    }
    
    @Test
    public void reachedDeuce() {
        TennisGame game = TennisGameTests.get();
        assertFalse(TennisMatchManager.reachedDeuce(game));
        for (int i = 0; i < 3; i++) 
            game.addPoint(game.getPlayerOne()).addPoint(game.getPlayerTwo()); 
        assertTrue(TennisMatchManager.reachedDeuce(game));
        game.addPoint(game.getPlayerOne());  
        assertTrue(TennisMatchManager.reachedDeuce(game));
        game.addPoint(game.getPlayerTwo()); 
        assertTrue(TennisMatchManager.reachedDeuce(game));
    }

    @Test
    public void testGetNonDeuceGameScore() {
        assertEquals(TennisMatchManager.getNonDeuceGameScore(0), TennisPlayerScore.ZERO);
        assertEquals(TennisMatchManager.getNonDeuceGameScore(1), TennisPlayerScore.FIFTEEN);
        assertEquals(TennisMatchManager.getNonDeuceGameScore(2), TennisPlayerScore.THIRTY);
        assertEquals(TennisMatchManager.getNonDeuceGameScore(3), TennisPlayerScore.FORTY);
        assertEquals(TennisMatchManager.getNonDeuceGameScore(4), TennisPlayerScore.FORTY);
        assertEquals(TennisMatchManager.getNonDeuceGameScore(5), TennisPlayerScore.FORTY);
    }
    
    private static void testScore(TennisGame game, String playerOneScore, String playerTwoScore){        
        TennisScore s = TennisMatchManager.getScore(game);
        assertEquals(s.getPlayerOneScore().getScore(), playerOneScore);
        assertEquals(s.getPlayerTwoScore().getScore(), playerTwoScore);
    }
    
    private static void testScore(TennisSet tennisSet, int playerOneScore, int playerTwoScore){        
        TennisScore s = TennisMatchManager.getScore(tennisSet);
        assertEquals(s.getPlayerOneScore().getScore(), String.valueOf(playerOneScore));
        assertEquals(s.getPlayerTwoScore().getScore(), String.valueOf(playerTwoScore));
    }
    
    public static TennisSet addGame(TennisSet tennisSet, TennisPlayer player){
        TennisSetTests.addGame(tennisSet, player);
        return tennisSet;
    }
    
    @Test
    public void getScore_Set(){
        TennisSet set = TennisSetTests.get();        
        testScore(set, 0, 0);
        int p1=0, p2=0;
        for (int i = 0; i < 6; i++) {            
            testScore(addGame(set, set.getPlayerOne()), ++p1, p2);
            if(i<4)
                testScore(addGame(set, set.getPlayerTwo()), p1, ++p2);
        }
    }
	
    @Test
    public void getScore_justBeforeFirstMatchGame(){
		fail("not yet implemeted - or test");
	}
	
    @Test
    public void getScore_justAfterCompletedFirstSet(){
		fail("not yet implemeted - or test");
	}
    
    @Test
    public void getScore_Game(){
        TennisGame game = TennisGameTests.get();        
        testScore(game, TennisPlayerScore.ZERO, TennisPlayerScore.ZERO);
        testScore(game.addPoint(game.getPlayerOne()), TennisPlayerScore.FIFTEEN, TennisPlayerScore.ZERO);
        testScore(game.addPoint(game.getPlayerTwo()), TennisPlayerScore.FIFTEEN, TennisPlayerScore.FIFTEEN);
        testScore(game.addPoint(game.getPlayerOne()), TennisPlayerScore.THIRTY, TennisPlayerScore.FIFTEEN);
        testScore(game.addPoint(game.getPlayerTwo()), TennisPlayerScore.THIRTY, TennisPlayerScore.THIRTY);
        testScore(game.addPoint(game.getPlayerOne()), TennisPlayerScore.FORTY, TennisPlayerScore.THIRTY);
        testScore(game.addPoint(game.getPlayerTwo()), TennisPlayerScore.FORTY, TennisPlayerScore.FORTY);
        testScore(game.addPoint(game.getPlayerOne()), TennisPlayerScore.ADVANTAGE, TennisPlayerScore.FORTY);
        testScore(game.addPoint(game.getPlayerTwo()), TennisPlayerScore.FORTY, TennisPlayerScore.FORTY);
        testScore(game.addPoint(game.getPlayerTwo()), TennisPlayerScore.FORTY, TennisPlayerScore.ADVANTAGE);
        testScore(game.addPoint(game.getPlayerOne()), TennisPlayerScore.FORTY, TennisPlayerScore.FORTY);
        testScore(game.addPoint(game.getPlayerOne()), TennisPlayerScore.ADVANTAGE, TennisPlayerScore.FORTY);
        testScore(game.addPoint(game.getPlayerOne()), TennisPlayerScore.GAME, TennisPlayerScore.FORTY);
    }
    
}
