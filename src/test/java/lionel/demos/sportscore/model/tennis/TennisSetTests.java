package lionel.demos.sportscore.model.tennis;

import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 * @author lionel.ngounou
 */
public class TennisSetTests {
    
    public TennisSetTests() {}
    
    @Test
    public void testComparison(){
        TennisMatch tennisMatch = Mockito.mock(TennisMatch.class);
        Mockito.when(tennisMatch.getId()).thenReturn("1");
        Mockito.when(tennisMatch.isMatchPlayer(null)).thenReturn(true);
        TennisSet tennisSet1 = new TennisSet(tennisMatch, 1, null);
        TennisSet tennisSet2 = new TennisSet(tennisMatch, 2, null);
        assertTrue(tennisSet1.compareTo(tennisSet2)<0);
        assertTrue(tennisSet2.compareTo(tennisSet1)>0);
                
        TennisMatch tennisMatch2 = Mockito.mock(TennisMatch.class);
        Mockito.when(tennisMatch2.getId()).thenReturn("2");
        Mockito.when(tennisMatch2.isMatchPlayer(null)).thenReturn(true);
        TennisSet tennisSet3 = new TennisSet(tennisMatch2, 1, null);
        assertTrue(tennisSet2.compareTo(tennisSet3)<0);
    }
    
    @Test
    public void printSet(){
        TennisSet tennisSet = get();   
        while(!tennisSet.isOver()){
            tennisSet.addRandomPoint();
            TennisGame g = tennisSet.getLastGame().get();
            if(g.isOver())
                System.out.println("S\t" + tennisSet.getScore());
            else
                System.out.println("G\t\t" + g.getScore());
        }
    }
    
    @Test
    public void addPoint(){        
        TennisSet tennisSet = get();     
        assertFalse(tennisSet.hasAnyGame());
        tennisSet.addPoint(tennisSet.getPlayerOne());
        assertTrue(tennisSet.getGames().size()==1);
        addGame(tennisSet, tennisSet.getPlayerOne());
        assertTrue(tennisSet.getGames().size()>1);
        addGame(tennisSet, tennisSet.getPlayerTwo());
        assertTrue(tennisSet.getGames().size()>=2);
        
        tennisSet = get();     
        addGame(tennisSet, tennisSet.getPlayerOne());
        assertTrue(tennisSet.getGames().size()==1);
        addGame(tennisSet, tennisSet.getPlayerTwo());
        assertTrue(tennisSet.getGames().size()==2);
    }
    
    public static void addGame(TennisSet tennisSet, TennisPlayer player){
        for (int j = 0; j < TennisPoint.MIN_NUMBER; j++)
            tennisSet.addPoint(player);
    }
    
    @Test
    public void isOver(){
        TennisSet tennisSet = get();      
        for (int i = 0; i < 4; i++) {
            addGame(tennisSet, tennisSet.getPlayerOne());
            addGame(tennisSet, tennisSet.getPlayerTwo());
            assertFalse(tennisSet.isOver());
        }
        addGame(tennisSet, tennisSet.getPlayerOne());
        assertFalse(tennisSet.isOver());
        addGame(tennisSet, tennisSet.getPlayerOne());
        assertTrue(tennisSet.isOver());
    }
    
    @Test
    public void removeLastPoint(){
        TennisSet tennisSet = get(); 
        assertFalse(tennisSet.hasAnyGame());
        tennisSet.addPoint(tennisSet.getPlayerOne());
        assertTrue(tennisSet.getLastGame().get().getPoints().size()==1);
        tennisSet.addPoint(tennisSet.getPlayerOne());
        assertTrue(tennisSet.getLastGame().get().getPoints().size()==2);
        tennisSet.removeLastPoint();
        assertTrue(tennisSet.getLastGame().get().getPoints().size()==1);
        tennisSet.removeLastPoint();
        assertFalse(tennisSet.hasAnyGame());
    }
    
    @Test
    public void getLastGame(){
        TennisSet tennisSet = get(); 
        assertFalse(tennisSet.getLastGame().isPresent());
        tennisSet.addPoint(tennisSet.getPlayerOne());
        Optional<TennisGame> optionalGame1 = tennisSet.getLastGame();
        assertTrue(optionalGame1.isPresent());
        addGame(tennisSet, tennisSet.getPlayerOne());
        Optional<TennisGame> optionalGame2 = tennisSet.getLastGame();
        assertTrue(optionalGame2.isPresent());
        assertNotEquals(optionalGame2.get(), optionalGame1.get());
    }
    
    public static TennisSet get(){
        TennisMatch match = new TennisMatch(new TennisPlayer("One", "p1"), new TennisPlayer("Two", "p2"), "m1");
        return new TennisSet(match, 1, match.getPlayerOne());
    }
}
