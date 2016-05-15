package lionel.demos.sportscore.model.tennis;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 * @author lionel.ngounou
 */
public class TennisPointTests {
    
    public TennisPointTests() {}
    
    @Test
    public void testComparison(){
        TennisGame tennisGame = Mockito.mock(TennisGame.class);
        Mockito.when(tennisGame.compareTo(tennisGame)).thenReturn(0);
        Mockito.when(tennisGame.compareTo(null)).thenReturn(0);
        TennisPoint tennisPoint1 = new TennisPoint(tennisGame, 1, true);
        TennisPoint tennisPoint2 = new TennisPoint(tennisGame, 2, true);
        assertTrue(tennisPoint1.compareTo(tennisPoint2)<0);
        assertTrue(tennisPoint2.compareTo(tennisPoint1)>0);
                
        TennisGame tennisGame2 = Mockito.mock(TennisGame.class);
        Mockito.when(tennisGame2.compareTo(tennisGame)).thenReturn(-1);
        Mockito.when(tennisGame2.compareTo(tennisGame2)).thenReturn(-1);
        Mockito.when(tennisGame2.compareTo(null)).thenReturn(-1);
        TennisPoint tennisPoint3 = new TennisPoint(tennisGame2, 1, true);
        assertTrue(tennisPoint3.compareTo(tennisPoint1)<0);
        assertTrue(tennisPoint1.compareTo(tennisPoint3)>0);
    }    
    
    @Test
    public void testCreateWonPoint(){
        assertTrue(TennisPoint.newWonPoint(Mockito.mock(TennisGame.class), 1).isWon());
    }
    
    @Test
    public void testCreateLostPoint(){
        assertFalse(TennisPoint.newLostPoint(Mockito.mock(TennisGame.class), 1).isWon());
    }
}
