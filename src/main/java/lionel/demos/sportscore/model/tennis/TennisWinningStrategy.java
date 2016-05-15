package lionel.demos.sportscore.model.tennis;

import java.util.Optional;

/**
 * @author lionel.ngounou
 */
public class TennisWinningStrategy {
    
    private final int minWinValue, minWinGap;
    public static final TennisWinningStrategy TENNISGAME_WINNING_STRATEGY = new TennisWinningStrategy(TennisPoint.MIN_NUMBER, TennisPoint.MIN_GAP); 
    public static final TennisWinningStrategy TENNISSET_WINNING_STRATEGY = new TennisWinningStrategy(TennisGame.MIN_NUMBER, TennisGame.MIN_GAP);
    public static final TennisWinningStrategy TENNISMATCH_WINNING_STRATEGY = new TennisWinningStrategy(TennisSet.MIN_NUMBER, TennisSet.MIN_GAP); 
    public static final TennisWinningStrategy TENNISMATCH_SLAM_WINNING_STRATEGY = new TennisWinningStrategy(3, 1); 

    public TennisWinningStrategy(int minWinValue, int minWinGap) {
        this.minWinValue = minWinValue;
        this.minWinGap = minWinGap;
        verify();
    }
    
    @Override
    public String toString() {
        return "WinningStrategy{" + "minWinValue=" + minWinValue + ", minWinGap=" + minWinGap + '}';
    }
    
    public boolean isWin(PointsHolder points){
        return isWin(points.pointOne, points.pointTwo);
    }
    
    public boolean isWin(int pointOne, int pointTwo){
        if(pointOne<minWinValue && pointTwo<minWinValue)
            return false;
        return Math.abs(pointOne - pointTwo) >= minWinGap;
    }
    
    public Optional<Integer> getWinner(PointsHolder points){
        return getWinner(points.pointOne, points.pointTwo);
    }  
    
    public Optional<Integer> getWinner(int pointOne, int pointTwo){
        if(!isWin(pointOne, pointTwo))
            return Optional.<Integer>empty();
        return pointOne>pointTwo? Optional.of(pointOne) : Optional.of(pointTwo);
    }

    public int getMinWinGap() {
        return minWinGap;
    }

    public int getMinWinValue() {
        return minWinValue;
    }
    
    private void verify(){
        if(minWinValue<=0 || minWinGap<=0 || minWinValue<minWinGap)
            throw new IllegalArgumentException("Wrong values -> minWinValue="+minWinValue+", minWinGap="+minWinGap);
    }
    
    public static class PointsHolder {
        private final int pointOne, pointTwo;

        public PointsHolder(int pointOne, int pointTwo) {
            this.pointOne = pointOne;
            this.pointTwo = pointTwo;
        }

        public int getPointOne() {
            return pointOne;
        }

        public int getPointTwo() {
            return pointTwo;
        }
        
        public int getTotal(){
            return pointOne + pointTwo;
        }
        
        public boolean equalPoints(){
            return pointOne==pointTwo;
        }
        
    }
}
