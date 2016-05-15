package lionel.demos.sportscore.service.tennis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lionel.demos.sportscore.model.tennis.TennisMatch;
import lionel.demos.sportscore.model.tennis.TennisPlayer;
import org.springframework.stereotype.Service;

/**
 * @author lionel.ngounou
 */
@Service
public class TennisServiceInMemory implements TennisService {
    
    private static final Set<TennisPlayer> players = new HashSet<>();
    private static final Set<TennisMatch> matches = new HashSet<>();
    
    @Override
    public TennisMatch createMatch(TennisPlayer playerOne, TennisPlayer playerTwo){
        TennisMatch tm = TennisService.super.createMatch(playerOne, playerTwo);
        matches.add(tm);
        return tm;
    }
    
    @Override
    public TennisMatch createSlamMatch(TennisPlayer playerOne, TennisPlayer playerTwo){
        TennisMatch tm = TennisService.super.createSlamMatch(playerOne, playerTwo);
        matches.add(tm);
        return tm;
    }
    
    @Override
    public void addPoint(String matchId, TennisPlayer player) {
        getMatch(matchId).ifPresent((m) -> m.addPoint(player));
    }

    @Override
    public Optional<TennisMatch> getMatch(String matchId) {
        return matches.stream().filter(m -> m.getId().equalsIgnoreCase(matchId)).findFirst();
    }

    @Override
    public Collection<TennisPlayer> getAllPlayers() {
        return new HashSet(players);
    }

    @Override
    public Collection<TennisMatch> getAllMatches() {
        return new HashSet(matches);
    }

    @Override
    public Collection<TennisMatch> getLiveMatches() {
        return new HashSet(matches.stream().filter(m -> !m.isOver()).collect(Collectors.toSet()));
    }    
    
    @PostConstruct
    public void boot(){
        System.out.println("##### boot tennis service - in memory");
        TennisPlayer djok = new TennisPlayer("Novak", "Djokovic");
        TennisPlayer murr = new TennisPlayer("Andy", "Murray");
        TennisPlayer fed = new TennisPlayer("Roger", "Federer");
        TennisPlayer stan = new TennisPlayer("Stan", "Wawrinka");
        players.add(djok);
        players.add(murr);
        players.add(fed);
        players.add(stan);
        players.add(new TennisPlayer("Rafael", "Nadal"));
        players.add(new TennisPlayer("Kei", "Nishikori"));
        players.add(new TennisPlayer("Joel", "Tsonga"));
        players.add(new TennisPlayer("Tomas", "Berdich"));
        players.add(new TennisPlayer("David", "Ferrer"));
        players.add(new TennisPlayer("Richard", "Gasquet"));
        
        createMatch(murr, djok);
        createMatch(fed, stan);
    }
}
