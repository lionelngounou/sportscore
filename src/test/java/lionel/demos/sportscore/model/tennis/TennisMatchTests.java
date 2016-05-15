package lionel.demos.sportscore.model.tennis;

import org.junit.Test;

/**
 * @author lionel.ngounou
 */
public class TennisMatchTests {
    
    public TennisMatchTests() {}
        
    static void printGame(TennisGame game){
        if(!game.isOver())
            System.out.println("G\t\t\t" + game.getScore());
        else
            System.out.println("S\t\t" + game.getTennisSet().getScore());
    }   
    static void printSet(TennisSet set){
        if(set.isOver()){
            System.out.println("S\t\t" + set.getScore());
            System.out.println("M\t" + set.getTennisMatch().getScore());
        }
        else
            printGame(set.getLastGame().orElse(null));
    }
    
    @Test
    public void printMatch(){
        TennisMatch tennisMatch = get();   
        while(!tennisMatch.isOver()){
            tennisMatch.addRandomPoint();
            if(tennisMatch.isOver())
                System.out.println("M\t" + tennisMatch.getScore());
            else
                printSet(tennisMatch.getLastSet().get());
        }
    }
    
    /*@Test
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
    }*/
        
    public static void addSet(TennisMatch match, TennisPlayer player){
        for (int j = 0; j < (TennisGame.MIN_NUMBER * TennisPoint.MIN_NUMBER); j++)
            match.addPoint(player);
    }
    
    public static TennisMatch get(){
        return new TennisMatch(new TennisPlayer("One", "Nadal"), new TennisPlayer("Two", "Djok"), "m1");
    }
    
    public static TennisMatch getForSlam(){
        return new TennisMatch(new TennisPlayer("One", "Nadal"), 
                new TennisPlayer("Two", "Djok"), "m1", TennisWinningStrategy.TENNISMATCH_SLAM_WINNING_STRATEGY);
    }
}
