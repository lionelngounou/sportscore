package lionel.demos.sportscore.model.tennis;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author lionel.ngounou
 */
public class TennisWinningStrategyTests {
    
    public TennisWinningStrategyTests() {}
    
    @Test
    public void testConstruct(){
        assertNotNull(new TennisWinningStrategy(2, 1));
        assertNotNull(new TennisWinningStrategy(1, 1));
    }
    
    @Test
    public void testFailedConstruct(){
        testFailedConstruct(0,2);
        testFailedConstruct(2,0);    
        testFailedConstruct(1,2);  
        testFailedConstruct(-1,2); 
        testFailedConstruct(1,-2);  
    }
    
    public void testFailedConstruct(int minWinValue, int minWinGap){
        try {
            new TennisWinningStrategy(minWinValue, minWinGap); 
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }           
    }
    
    @Test
    public void testIsWin(){
        TennisWinningStrategy tws = new TennisWinningStrategy(1, 1);
        assertTrue(tws.isWin(1, 0));
        assertTrue(tws.isWin(0, 1));
        assertFalse(tws.isWin(1, 1));
        
        tws = new TennisWinningStrategy(6, 2);
        assertFalse(tws.isWin(1, 1));
        assertFalse(tws.isWin(5, 1));
        assertFalse(tws.isWin(3, 5));
        assertFalse(tws.isWin(5, 5));
        assertFalse(tws.isWin(6, 5));
        assertFalse(tws.isWin(6, 6));
        assertFalse(tws.isWin(6, 7));
        assertFalse(tws.isWin(7, 7));
        assertFalse(tws.isWin(7, 8));
        
        assertTrue(tws.isWin(6, 3));
        assertTrue(tws.isWin(3, 6));
        assertTrue(tws.isWin(4, 6));
        assertTrue(tws.isWin(6, 4));
        assertTrue(tws.isWin(5, 7));
        assertTrue(tws.isWin(7, 5));
        assertTrue(tws.isWin(6, 8));
        assertTrue(tws.isWin(8, 6));
    }
    
    @Test
    public void testGetWinnner(){
        TennisWinningStrategy tws = new TennisWinningStrategy(6, 2);
        assertFalse(tws.getWinner(3, 4).isPresent());
        assertFalse(tws.getWinner(5, 4).isPresent());
        assertFalse(tws.getWinner(5, 5).isPresent());
        
        assertTrue(tws.getWinner(7, 5).get()==7);
        assertTrue(tws.getWinner(5, 7).get()==7);
    }
}
