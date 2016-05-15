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
public class TennisMatch {
    
    private final TennisPlayer playerOne, playerTwo;
    private final String id;
    private final TennisWinningStrategy tennisWinningStrategy ;
    private final SortedSet<TennisSet> sets;
    private volatile TennisScore latestScore;

    public TennisMatch(TennisPlayer playerOne, TennisPlayer playerTwo, String id) {
        this(playerOne, playerTwo, id, TennisWinningStrategy.TENNISMATCH_WINNING_STRATEGY);
    }
    
    public TennisMatch(TennisPlayer playerOne, TennisPlayer playerTwo, String id, TennisWinningStrategy tennisWinningStrategy) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.id = id;
        this.tennisWinningStrategy = tennisWinningStrategy;
        this.sets = new ConcurrentSkipListSet();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final TennisMatch other = (TennisMatch) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }    
    
    public String getId() {
        return id;
    }

    public TennisWinningStrategy getTennisWinningStrategy() {
        return tennisWinningStrategy;
    }

    public Set<TennisSet> getSets() {
        return Collections.unmodifiableSet(sets);
    }  

    public TennisPlayer getPlayerOne() {
        return playerOne;
    }

    public TennisPlayer getPlayerTwo() {
        return playerTwo;
    }
    
    public boolean hasAnySet(){
        return !sets.isEmpty();
    }
    
    public boolean isOver(){
        return TennisMatchManager.isOver(this);
    }  
    
    public TennisScore getScore(){
        if(latestScore==null)
            updateLatestScore();
        return latestScore;
    }
    
    private void updateLatestScore(){
        latestScore = TennisMatchManager.getScore(this);
    }
    
    public Optional<TennisPlayer> getWinner(){
        return TennisMatchManager.getWinner(this);
    }
    
    public Optional<TennisSet> getLastSet(){
        return sets.isEmpty()? Optional.empty(): Optional.of(sets.last());
    }
    
    public Optional<TennisGame> getLastGame(){
        Optional<TennisSet> optLastSet = getLastSet();
        return optLastSet.isPresent()? optLastSet.get().getLastGame() : Optional.empty();
    }
    
    protected boolean isMatchPlayer(TennisPlayer player){
        return player.equals(playerOne) || player.equals(playerTwo);
    }
    
    protected void check(TennisPlayer player){
        if(!isMatchPlayer(player))
            throw new IllegalArgumentException("Player " + player.toString() + " is not part of this match");
    }
    
    public TennisMatch addPoint(TennisPlayer player){
        check(player);
        if(!isOver()){
            TennisSet set = getLastSet().orElse(null);
            if(set==null)
                sets.add(new TennisSet(this, 1, getPlayerOne()).addPoint(player));                
            else if(set.isOver()){
                TennisPlayer nextToServe = set.getLastGame().get().getOpponentPlayer();
                sets.add(new TennisSet(this, sets.size() + 1, nextToServe).addPoint(player));
            }
            else
                set.addPoint(player);
            updateLatestScore();
        }
        return this;
    }
    
    public TennisMatch addRandomPoint(){
        return addPoint(RandomUtils.nextBoolean()? getPlayerOne() : getPlayerTwo());
    }
    
    public TennisMatch removeLastPoint(){
        Optional<TennisSet> optionalSet  = getLastSet();
        if(optionalSet.isPresent()){
            TennisSet set = optionalSet.get();
            set.removeLastPoint();
            if(!set.hasAnyGame())
                sets.removeIf(s ->  s.equals(set));
            updateLatestScore();
        }
        return this;
    }
    
    public TennisMatch abandon(TennisPlayer player){
        check(player);
        updateLatestScore();
        return this;
    }
        
}
