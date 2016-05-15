package lionel.demos.sportscore.model.tennis;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 * @author lionel.ngounou
 */
public class TennisGameTests {
    
    public TennisGameTests() {}
    
    @Test
    public void testComparison(){
        TennisSet tennisSet = Mockito.mock(TennisSet.class);
        Mockito.when(tennisSet.compareTo(tennisSet)).thenReturn(0);
        Mockito.when(tennisSet.compareTo(null)).thenReturn(0);
        TennisGame tennisGame1 = new TennisGame(tennisSet, 1, null);
        TennisGame tennisGame2 = new TennisGame(tennisSet, 2, null);
        assertTrue(tennisGame1.compareTo(tennisGame2)<0);
        assertTrue(tennisGame2.compareTo(tennisGame1)>0);
                
        TennisSet tennisSet2 = Mockito.mock(TennisSet.class);
        Mockito.when(tennisSet2.compareTo(tennisSet)).thenReturn(-1);
        Mockito.when(tennisSet2.compareTo(tennisSet2)).thenReturn(-1);
        Mockito.when(tennisSet2.compareTo(null)).thenReturn(-1);
        TennisGame tennisGame3 = new TennisGame(tennisSet2, 1, null);
        assertTrue(tennisGame3.compareTo(tennisGame1)<0);
        assertTrue(tennisGame1.compareTo(tennisGame3)>0);
    }
    
    @Test
    public void getPreviousPoint(){
        TennisGame tennisGame = get();     
        assertFalse(tennisGame.getLastPoint().isPresent());
        
        tennisGame.addPoint(tennisGame.getOpponentPlayer());
        int num = 1;
        assertTrue(tennisGame.getPoints().size()==num);
        TennisPoint p = tennisGame.getLastPoint().get();
        assertTrue(!p.isWon());
        assertTrue(p.getNumber()==num);
        
        tennisGame.addPoint(tennisGame.getOpponentPlayer());
        num ++;
        assertTrue(tennisGame.getPoints().size()==num);
        p = tennisGame.getLastPoint().get();
        assertTrue(!p.isWon());
        assertTrue(p.getNumber()==num);
        
        tennisGame.addPoint(tennisGame.getServicePlayer());
        num ++;
        assertTrue(tennisGame.getPoints().size()==num);
        p = tennisGame.getLastPoint().get();
        assertTrue(p.isWon());
        assertTrue(p.getNumber()==num);
        
    }
    
    @Test
    public void addPoint(){        
        TennisGame tennisGame = get();        
        for (int i = 1; i <= 2; i++) {
            tennisGame.addPoint(tennisGame.getOpponentPlayer()).addPoint(tennisGame.getServicePlayer());
            assertTrue((i*2)==tennisGame.getPoints().size());
        }
        tennisGame.addPoint(tennisGame.getServicePlayer());
        assertTrue(5==tennisGame.getPoints().size());
        tennisGame.addPoint(tennisGame.getServicePlayer());
        assertTrue(6==tennisGame.getPoints().size());
        
        assertTrue(tennisGame.isOver());
        tennisGame.addPoint(tennisGame.getServicePlayer());
        assertTrue("cannot add more when it's over", 6==tennisGame.getPoints().size());
    }
    
    @Test
    public void isOver(){
        TennisGame tennisGame = get();        
        for (int i = 0; i < 2; i++) {
            tennisGame.addPoint(tennisGame.getOpponentPlayer());
            assertFalse(tennisGame.isOver());
            tennisGame.addPoint(tennisGame.getServicePlayer());
            assertFalse(tennisGame.isOver());
        }
        tennisGame.addPoint(tennisGame.getServicePlayer());
        assertFalse(tennisGame.isOver());
        tennisGame.addPoint(tennisGame.getServicePlayer());
        assertTrue(tennisGame.isOver());//should be over with 4:2        
    }
    
    @Test
    public void removeLastPoint(){
        TennisGame tennisGame = get();
        tennisGame.removeLastPoint();
        assertTrue(tennisGame.getPoints().isEmpty());
        tennisGame.addPoint(tennisGame.getPlayerOne());
        assertTrue(1==tennisGame.getPoints().size());
        tennisGame.removeLastPoint();
        assertTrue(tennisGame.getPoints().isEmpty());
    }
    
    public static TennisSet mockSet(TennisPlayer playerOne, TennisPlayer playerTwo){
        TennisSet tennisSet = Mockito.mock(TennisSet.class);
        Mockito.when(tennisSet.isMatchPlayer(playerOne)).thenReturn(true);
        Mockito.when(tennisSet.isMatchPlayer(playerTwo)).thenReturn(true);
        Mockito.when(tennisSet.isMatchPlayer(null)).thenReturn(true);
        Mockito.when(tennisSet.getPlayerOne()).thenReturn(playerOne);
        Mockito.when(tennisSet.getPlayerTwo()).thenReturn(playerTwo);
        return tennisSet;
    }
    
    public static TennisGame get(){
        return get(new TennisPlayer("One", "1"), new TennisPlayer("Two", "2"));
    }
    
    public static TennisGame get(TennisPlayer playerOne, TennisPlayer playerTwo){
        return new TennisGame(mockSet(playerOne, playerTwo), 1, playerOne);
    }
}
