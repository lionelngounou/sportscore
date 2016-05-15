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
public class TennisSet implements Comparable<TennisSet>{
    
    public static final int MIN_NUMBER = 2; 
    public static final int MIN_GAP = 1; 
    
    private final TennisMatch tennisMatch;
    private final int number;
    private final TennisPlayer servicePlayer;
    private final TennisWinningStrategy tennisWinningStrategy ;
    private final SortedSet<TennisGame> games;
    private volatile TennisScore latestScore;

    public TennisSet(TennisMatch tennisMatch, int number, TennisPlayer servicePlayer) {
        this(tennisMatch, number, servicePlayer, TennisWinningStrategy.TENNISSET_WINNING_STRATEGY);
    }
    
    public TennisSet(TennisMatch tennisMatch, int number, TennisPlayer servicePlayer, TennisWinningStrategy tennisWinningStrategy) {
        this.tennisMatch = tennisMatch;
        this.number = number;
        this.tennisWinningStrategy = tennisWinningStrategy;
        this.servicePlayer = servicePlayer;
        this.games = new ConcurrentSkipListSet();
        check(servicePlayer);
    }

    public TennisPlayer getServicePlayer() {
        return servicePlayer;
    }
    
    public TennisMatch getTennisMatch() {
        return tennisMatch;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(TennisSet o) {
        if(this.equals(o)) return 0;
        int c = tennisMatch.getId().compareTo(o.tennisMatch.getId());
        return c==0? ((Integer)this.number).compareTo(o.number): c;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.tennisMatch);
        hash = 53 * hash + this.number;
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
        final TennisSet other = (TennisSet) obj;
        if (!Objects.equals(this.tennisMatch, other.tennisMatch)) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        return true;
    }

    public Set<TennisGame> getGames() {
        return Collections.unmodifiableSet(games);
    }
    
    public boolean hasAnyGame(){
        return !games.isEmpty();
    }

    public TennisWinningStrategy getTennisWinningStrategy() {
        return tennisWinningStrategy;
    }
    
    protected boolean isMatchPlayer(TennisPlayer player){
        return tennisMatch.isMatchPlayer(player);
    }
    
    protected final void check(TennisPlayer player){
        if(!isMatchPlayer(player))
            throw new IllegalArgumentException("Player " + player.toString() + " is not part of this set");
    }
    
    public TennisPlayer getPlayerOne(){
        return tennisMatch.getPlayerOne();
    }
    
    public TennisPlayer getPlayerTwo(){
        return tennisMatch.getPlayerTwo();
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
    
    public Optional<TennisGame> getLastGame(){
        return games.isEmpty()? Optional.empty(): Optional.of(games.last());
    }
    
    public TennisSet addPoint(TennisPlayer player){
        check(player);
        if(!isOver()){
            Optional<TennisGame> optionalGame = getLastGame(); 
            if(!optionalGame.isPresent())
                games.add(new TennisGame(this, 1, servicePlayer).addPoint(player));                
            else if(optionalGame.get().isOver())
                games.add(new TennisGame(this, games.size() + 1, optionalGame.get().getOpponentPlayer()).addPoint(player));
            else
                optionalGame.get().addPoint(player);
            updateLatestScore();
        }
        return this;
    }    
    
    public TennisSet addRandomPoint(){
        return addPoint(RandomUtils.nextBoolean()? getPlayerOne() : getPlayerTwo());
    }
    
    public TennisSet removeLastPoint(){
        getLastGame().ifPresent(game -> {
            game.removeLastPoint();
            if(!game.hasAnyPoint())
                games.removeIf(g ->  g.equals(game));
            updateLatestScore();
        });
        return this;
    }
}
