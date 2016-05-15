package lionel.demos.sportscore.service.tennis;

import java.util.Collection;
import java.util.Optional;
import lionel.demos.sportscore.model.tennis.TennisMatch;
import lionel.demos.sportscore.model.tennis.TennisPlayer;
import lionel.demos.sportscore.util.tennis.TennisMatchManager;

/**
 * @author lionel.ngounou
 */
public interface TennisService {
    
    public void addPoint(String matchId, TennisPlayer player);
    
    default TennisMatch createMatch(TennisPlayer playerOne, TennisPlayer playerTwo){
        return TennisMatchManager.create2SetsMatch(playerOne, playerTwo);
    }
    
    default TennisMatch createSlamMatch(TennisPlayer playerOne, TennisPlayer playerTwo){
        return TennisMatchManager.create3SetsMatch(playerOne, playerTwo);
    }
    
    public Optional<TennisMatch> getMatch(String matchId);
    
    public Collection<TennisPlayer> getAllPlayers();
    
    public Collection<TennisMatch> getAllMatches();
    
    public Collection<TennisMatch> getLiveMatches();
    
}
