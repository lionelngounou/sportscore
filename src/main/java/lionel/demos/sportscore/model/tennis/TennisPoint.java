package lionel.demos.sportscore.model.tennis;

import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang.math.RandomUtils;

/**
 * @author lionel.ngounou
 */
public class TennisPoint implements Comparable<TennisPoint>, Serializable {
        
    public static final int MIN_NUMBER = 4;
    public static final int DEUCE_NUMBER = MIN_NUMBER - 1; 
    public static final int MIN_GAP = 2; 
    
    private final TennisGame tennisGame;
    private final int number;
    private boolean won;//designates if the serving player has won the point
    
    public TennisPoint(TennisGame tennisGame, int number, boolean won) {
        this.tennisGame = tennisGame;
        this.number = number;
        this.won = won;
    }    
    
    public static TennisPoint newWonPoint(TennisGame tennisGame, int number){
        return new TennisPoint(tennisGame, number, true);
    }
    
    public static TennisPoint newLostPoint(TennisGame tennisGame, int number){
        return new TennisPoint(tennisGame, number, false);
    }

    @Override
    public String toString() {
        return "TennisPoint{" + "tennisGame=" + tennisGame + ", number=" + number + ", won=" + won + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.tennisGame);
        hash = 67 * hash + this.number;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TennisPoint other = (TennisPoint) obj;
        if (!Objects.equals(this.tennisGame, other.tennisGame)) {
            return false;
        }
        return this.number == other.number;
    }
    
    @Override
    public int compareTo(TennisPoint o) {
        if(this.equals(o)) 
            return 0;
        int c = tennisGame.compareTo(o.tennisGame);
        return c==0? ((Integer)this.number).compareTo(o.number) : c;
    }
    
    public TennisGame getTennisGame() {
        return tennisGame;
    }

    public int getNumber() {
        return number;
    }

    protected void setWon(boolean won) {
        this.won = won;
    }
    
    protected void setRandomWon() {
        setWon(RandomUtils.nextBoolean());
    }

    public boolean isWon() { //null means the point is not yet over
        return won;
    }
    
    public TennisPlayer getWinningPlayer(){
        return isWon()? tennisGame.getServicePlayer() : tennisGame.getOpponentPlayer();
    }
}
