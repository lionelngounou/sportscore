package lionel.demos.sportscore.model.tennis;

import java.io.Serializable;

/**
 * @author lionel.ngounou
 */
public class TennisScore implements Serializable {
    
    private final TennisPlayerScore playerOneScore, playerTwoScore;

    public TennisScore(TennisPlayerScore playerOneScore, TennisPlayerScore playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
        check();
    }
    
    public TennisScore(TennisPlayer playerOne, String scoreOne, TennisPlayer playerTwo, String scoreTwo) {
        this(new TennisPlayerScore(playerOne, scoreOne), new TennisPlayerScore(playerTwo, scoreTwo));
    }

    @Override
    public String toString() {
        return playerOneScore + " | " + playerTwoScore;
    }
    
    public TennisPlayerScore getPlayerOneScore() {
        return playerOneScore;
    }

    public TennisPlayerScore getPlayerTwoScore() {
        return playerTwoScore;
    }
    
    protected final void check(){
        if(playerOneScore.getPlayer().equals(playerTwoScore.getPlayer()))
            throw new IllegalArgumentException("Score cannot hold the same players");
    }
        
    public static class TennisPlayerScore implements Serializable {
    
        private final TennisPlayer player;
        private final String score;
        
        public static final String ZERO = "0 ";
        public static final String FIFTEEN = "15";
        public static final String THIRTY = "30";
        public static final String FORTY = "40";
        public static final String ADVANTAGE = "A ";
        public static final String GAME = "W ";

        public TennisPlayerScore(TennisPlayer player, String score) {
            this.player = player;
            this.score = score;
        }   

        public TennisPlayer getPlayer() {
            return player;
        }

        public String getScore() {
            return score;
        }

        @Override
        public String toString() {
            return player.getShortCode()+ ":" + score; 
        }
        
    }
}
