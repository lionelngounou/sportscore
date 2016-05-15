package lionel.demos.sportscore.model.tennis;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import lionel.demos.sportscore.util.tennis.TennisMatchManager;
import org.apache.commons.lang.math.RandomUtils;

/**
 * @author lionel.ngounou
 */
public class TennisGame implements Comparable<TennisGame>{
    
    public static final int MIN_NUMBER = 6; 
    public static final int MIN_GAP = 2; 
    
    private final TennisPlayer servicePlayer; 
    private final int number;
    private final TennisSet tennisSet;
    private final SortedSet<TennisPoint> points;
    private final TennisWinningStrategy tennisWinningStrategy ;
    private volatile TennisScore latestScore;
    
    public TennisGame(TennisSet tennisSet, int number, TennisPlayer servicePlayer) {
        this(tennisSet, number, servicePlayer, TennisWinningStrategy.TENNISGAME_WINNING_STRATEGY);
    }
    
    public TennisGame(TennisSet tennisSet, int number, TennisPlayer servicePlayer, TennisWinningStrategy tennisWinningStrategy) {
        this.tennisSet = tennisSet;
        this.number = number;
        this.servicePlayer = servicePlayer;
        this.tennisWinningStrategy = tennisWinningStrategy;
        this.points = new ConcurrentSkipListSet();
    }

    @Override
    public String toString() {
        return "TennisGame{" + "tennisSet=" + tennisSet + ", number=" + number + ", servicePlayer=" + servicePlayer + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.tennisSet);
        hash = 59 * hash + this.number;
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
        final TennisGame other = (TennisGame) obj;
        if (!Objects.equals(this.tennisSet, other.tennisSet)) {
            return false;
        }
        return this.number == other.number;
    }

    @Override
    public int compareTo(TennisGame o) {
        if(this.equals(o)) return 0;
        int c = tennisSet.compareTo(o.tennisSet);
        return c==0? ((Integer)this.number).compareTo(o.number) : c;
    }

    public TennisWinningStrategy getTennisWinningStrategy() {
        return tennisWinningStrategy;
    }
        
    public TennisPlayer getServicePlayer() {
        return servicePlayer;
    }
        
    public TennisPlayer getOpponentPlayer() {
        TennisPlayer playerOne = getPlayerOne();
        return servicePlayer.equals(playerOne)? getPlayerTwo() : playerOne;
    }
    
    public TennisPlayer getPlayerOne(){
        return tennisSet.getPlayerOne();
    }
    
    public TennisPlayer getPlayerTwo(){
        return tennisSet.getPlayerTwo();
    }
    
    public boolean playerOneIsServing(){
        return servicePlayer.equals(getPlayerOne());
    }
        
    public TennisSet getTennisSet() {
        return tennisSet;
    }

    public int getNumber() {
        return number;
    }

    public Set<TennisPoint> getPoints() {
        return Collections.unmodifiableSet(points);
    }
    
    public boolean hasAnyPoint(){
        return !points.isEmpty();
    }
    
    protected boolean isMatchPlayer(TennisPlayer player){
        return tennisSet.isMatchPlayer(player);
    }
    
    protected void check(TennisPlayer player){
        if(!isMatchPlayer(player))
            throw new IllegalArgumentException("Player " + player.toString() + " is not part of this game");
    } 
    
    public boolean isOver(){
        return TennisMatchManager.isOver(this);
    }    
    
    public Optional<TennisPoint> getLastPoint(){
        return points.isEmpty()? Optional.empty() : Optional.of(points.last());
    }
    
    public TennisGame addPoint(TennisPlayer player){
        check(player);
        if(!isOver()){
            points.add(new TennisPoint(this, points.size() + 1, player.equals(servicePlayer)));
            updateLatestScore();
        }
        return this;
    }
    
    public TennisGame addRandomPoint(){
        return addPoint(RandomUtils.nextBoolean()? getPlayerOne() : getPlayerTwo());
    }
    
    public TennisGame removeLastPoint(){
        if(points.removeIf(p -> p.equals(getLastPoint().orElse(null))))
            updateLatestScore();
        return this;
    }
    
    public Optional<TennisPlayer> getWinner(){
        return TennisMatchManager.getWinner(this);
    }
    
    public TennisScore getScore(){
        if(latestScore==null)
            updateLatestScore();
        return latestScore;
    }
    
    private void updateLatestScore(){
        latestScore = TennisMatchManager.getScore(this);
    }
            
}
