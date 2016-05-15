package lionel.demos.sportscore.util.tennis;

import java.util.Optional;
import lionel.demos.sportscore.model.tennis.TennisGame;
import lionel.demos.sportscore.model.tennis.TennisMatch;
import lionel.demos.sportscore.model.tennis.TennisPlayer;
import lionel.demos.sportscore.model.tennis.TennisPoint;
import lionel.demos.sportscore.model.tennis.TennisScore;
import lionel.demos.sportscore.model.tennis.TennisScore.TennisPlayerScore;
import lionel.demos.sportscore.model.tennis.TennisSet;
import lionel.demos.sportscore.model.tennis.TennisWinningStrategy;
import lionel.demos.sportscore.model.tennis.TennisWinningStrategy.PointsHolder;

/**
 * @author lionel.ngounou
 */
public class TennisMatchManager {
    
    private TennisMatchManager(){}
    
    public static TennisMatch create2SetsMatch(TennisPlayer playerOne, TennisPlayer playerTwo){
        return new TennisMatch(playerOne, playerTwo, generateId(playerOne,playerTwo));
    }
    
    public static TennisMatch create3SetsMatch(TennisPlayer playerOne, TennisPlayer playerTwo){
        return new TennisMatch(playerOne, playerTwo, generateId(playerOne,playerTwo), TennisWinningStrategy.TENNISMATCH_SLAM_WINNING_STRATEGY);
    }
    
    public static String generateId(String txtOne, String txtTwo){
        return txtOne + System.currentTimeMillis() + txtTwo;
    }
    
    public static String generateId(TennisPlayer playerOne, TennisPlayer playerTwo){
        return generateId(playerOne.getShortCode(), playerTwo.getShortCode());
    }
    
    public static boolean isOver(TennisMatch match){
        return match.getTennisWinningStrategy().isWin(getPointsHolder(match));
    }
    
    public static boolean isOver(TennisGame game){
        return game.getTennisWinningStrategy().isWin(getPointsHolder(game));
    }
    
    public static boolean isOver(TennisSet tennisSet){
        return tennisSet.getTennisWinningStrategy().isWin(getPointsHolder(tennisSet));
    }
    
    public static Optional<TennisPlayer> getWinner(TennisGame game){        
        PointsHolder ph = getPointsHolder(game);
        if(!game.getTennisWinningStrategy().isWin(ph))
            return Optional.empty();
        return ph.getPointOne()>ph.getPointTwo()? Optional.of(game.getPlayerOne()): Optional.of(game.getPlayerTwo());
    }
    
    public static Optional<TennisPlayer> getWinner(TennisSet tennisSet){        
        PointsHolder ph = getPointsHolder(tennisSet);
        if(!tennisSet.getTennisWinningStrategy().isWin(ph))
            return Optional.empty();
        return ph.getPointOne()>ph.getPointTwo()? Optional.of(tennisSet.getPlayerOne()): Optional.of(tennisSet.getPlayerTwo());
    }
    
    public static Optional<TennisPlayer> getWinner(TennisMatch match){        
        PointsHolder ph = getPointsHolder(match);
        if(!match.getTennisWinningStrategy().isWin(ph))
            return Optional.empty();
        return ph.getPointOne()>ph.getPointTwo()? Optional.of(match.getPlayerOne()): Optional.of(match.getPlayerTwo());
    }
        
    public static PointsHolder getPointsHolder(TennisGame game){
        int pointOne = 0, pointTwo = 0;
        for (TennisPoint p : game.getPoints()) {
            if(game.playerOneIsServing()){
                if(p.isWon()) pointOne++;
                else pointTwo++;
            }
            else{
                if(p.isWon()) pointTwo++;
                else pointOne++;
            }
        }
        return new TennisWinningStrategy.PointsHolder(pointOne, pointTwo);
    }
        
    public static PointsHolder getPointsHolder(TennisMatch match){
        int pointOne = 0, pointTwo = 0;
        for (TennisSet s : match.getSets()) {
            Optional<TennisPlayer> op = s.getWinner();
            if(op.isPresent()){
                if(match.getPlayerOne().equals(op.get()))
                    pointOne++;
                else if(match.getPlayerTwo().equals(op.get()))
                    pointTwo++;                
            }
        }
        return new TennisWinningStrategy.PointsHolder(pointOne, pointTwo);
    }
        
    public static PointsHolder getPointsHolder(TennisSet tennisSet){
        int pointOne = 0, pointTwo = 0;
        for (TennisGame g : tennisSet.getGames()) {
            Optional<TennisPlayer> o = g.getWinner();
            if(!o.isPresent())
                continue;
            if(g.getPlayerOne().equals(o.get())) pointOne++;
            else pointTwo++;
        }
        return new TennisWinningStrategy.PointsHolder(pointOne, pointTwo);
    }
    
    public static TennisScore getScore(TennisSet tennisSet){
        PointsHolder ph = getPointsHolder(tennisSet);        
        return new TennisScore(tennisSet.getPlayerOne(), String.valueOf(ph.getPointOne()), 
                tennisSet.getPlayerTwo(), String.valueOf(ph.getPointTwo()));
    }    
    
    public static TennisScore getScore(TennisMatch tennisMatch){
        PointsHolder ph = getPointsHolder(tennisMatch);        
        return new TennisScore(tennisMatch.getPlayerOne(), String.valueOf(ph.getPointOne()), 
                tennisMatch.getPlayerTwo(), String.valueOf(ph.getPointTwo()));
    }
    
    public static TennisScore getScore(TennisGame game){
        TennisPlayer playerOne = game.getPlayerOne(), playerTwo = game.getPlayerTwo();
        PointsHolder ph = getPointsHolder(game);
        if(ph.getTotal()==0)
            return new TennisScore(playerOne, TennisPlayerScore.ZERO, playerTwo, TennisPlayerScore.ZERO);
        boolean won = game.getTennisWinningStrategy().isWin(ph);
        if(won){
           if(ph.getPointOne()>ph.getPointTwo())
               return new TennisScore(playerOne, TennisPlayerScore.GAME, playerTwo, getNonDeuceGameScore(ph.getPointTwo()));
            return new TennisScore(playerOne, getNonDeuceGameScore(ph.getPointOne()), playerTwo, TennisPlayerScore.GAME);
        }
        if(reachedDeuce(ph)){
            if(isDeuce(ph))
                return new TennisScore(playerOne, TennisPlayerScore.FORTY, playerTwo, TennisPlayerScore.FORTY);
            if(ph.getPointOne()>ph.getPointTwo())
                return new TennisScore(playerOne, TennisPlayerScore.ADVANTAGE, playerTwo, TennisPlayerScore.FORTY);
            return new TennisScore(playerOne, TennisPlayerScore.FORTY, playerTwo, TennisPlayerScore.ADVANTAGE);
        }
        return new TennisScore(playerOne, getNonDeuceGameScore(ph.getPointOne()), 
                playerTwo, getNonDeuceGameScore(ph.getPointTwo()));
    }
    
    public static boolean isDeuce(TennisGame game){
        return isDeuce(getPointsHolder(game));
    }
        
    public static boolean isDeuce(PointsHolder pointHolder){
        return pointHolder.equalPoints() && pointHolder.getPointOne()>=TennisPoint.DEUCE_NUMBER;
    }
    
    public static boolean reachedDeuce(TennisGame game){
        return reachedDeuce(getPointsHolder(game));
    }
    
    public static boolean reachedDeuce(PointsHolder pointHolder){
        return pointHolder.getPointOne()>=TennisPoint.DEUCE_NUMBER && pointHolder.getPointTwo()>=TennisPoint.DEUCE_NUMBER;
    }
    
    public static String getNonDeuceGameScore(final int points){
        switch(points){
            case 0: return TennisPlayerScore.ZERO;
            case 1: return TennisPlayerScore.FIFTEEN;
            case 2: return TennisPlayerScore.THIRTY;
            default: return TennisPlayerScore.FORTY;
        }
    }
    
}
